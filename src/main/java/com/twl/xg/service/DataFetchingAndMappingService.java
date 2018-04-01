package com.twl.xg.service;

import com.twl.xg.dao.BorderRouterRepository;
import com.twl.xg.dao.SensorDataRepository;
import com.twl.xg.dao.SensorRepository;
import com.twl.xg.domain.*;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class DataFetchingAndMappingService {
  @Autowired
  ApplicationContext context;
  @Autowired
  SessionFactory sessionFactory;
  @Autowired
  SensorDataRepository sensorDataRepository;
  @Autowired
  SensorRepository sensorRepository;
  @Autowired
  BorderRouterRepository borderRouterRepository;

  private static final Logger logger = Logger.getLogger(DataFetchingAndMappingService.class);

  /**
   * Update <code>dataTypeList</code> according to the input array. Every time the
   * <code>dataTypeList</code> gets updated, the <code>sensor_data</code> table
   * in database will be cleared. Because the number of data fields have been
   * changed, and it will be inconsistent.
   *
   * This method need to be synchronized to avoid race condition when this method
   * is called in multi-threads.
   *
   * Return value is the current <code>dataTypeList</code>, return <code>null</code>
   * if the input is invalid.
   *
   * After this initialize operation, the <code>currentDataTypeList</code> is sorted
   * in lexicographic order.
   *
   * @param dataTypeArray An array of data types string
   * @return Return a list of String which contains all data types name set by user.
   */
  @Transactional
  public synchronized List<String> setDataTypes(String[] dataTypeArray) {
    if (dataTypeArray == null || dataTypeArray.length == 0) {
      logger.error("setDataTypes: Invalid input");
      return null;
    }
    // check if the input data types are the same to the current data types.
    List<String> currentDataTypeList = (List<String>)context.getBean("dataTypeList");
    Set<String> currentDataTypeSet = new HashSet<>(currentDataTypeList);
    Set<String> inputDataTypeSet = new HashSet<>(Arrays.asList(dataTypeArray));
    if (currentDataTypeSet.equals(inputDataTypeSet)) {
      // if same, do nothing, return current dataTypeList
      logger.debug("setDataTypes: Input data types are the same to current data types");
      return currentDataTypeList;
    } else {
      // else, clear sensor_data table, update currentDataTypeList, and return it
      sensorDataRepository.clear();
      currentDataTypeList.clear();
      currentDataTypeList.addAll(inputDataTypeSet);
      Collections.sort(currentDataTypeList);
      logger.debug("setDataTypes: dataTypeList updated to: " + currentDataTypeList.toString());
      return currentDataTypeList;
    }
  }

  /**
   * Fetching data from database for given border router. Return all the data that
   * generated later than the given timeStamp;
   *
   * If the input time stamp is <code>null</code>, return all data for the router.
   *
   * Return <code>null</code> if the input border router IP doesn't exist in the
   * database or the size of the results is 0.
   *
   * @param borderRouterIp The IPv6 address of the border router you want to
   *                       fetch date from.
   * @param timeStamp      The earliest time stamp.
   * @return An instance of <code>BorderRouterWrapper</code> that contains a list
   * of <code>SensorDataEntity</code>.
   */
  @Transactional
  public BorderRouterWrapper getDataForBorderRouterFromDB(String borderRouterIp, Date timeStamp) {
    int dataCount = 0;

    // get borderRouter entity first
    BorderRouterEntity borderRouter = borderRouterRepository.get(borderRouterIp);
    if (borderRouter == null) {
      logger.error("getDataForBorderRouterFromDB:  The input border router IP doesn't exist, ip = " + borderRouterIp);
      return null;
    }
    // get all sensors connected to that border router
    List<SensorEntity> sensorList = sensorRepository.getAll(borderRouterIp);
    if (sensorList.isEmpty()) {
      logger.debug("getDataForBorderRouterFromDB:  No sensor connected to the border router, ip = " + borderRouterIp);
      return null;
    }
    // for each sensor, get its SensorWrapper, store them in list
    List<SensorWrapper> sensorWrapperList = new ArrayList<>();
    for (SensorEntity sensor : sensorList) {
      SensorWrapper sensorWrapper= getDataForSensorFromDB(sensor.getSensorIp(), timeStamp);
      if (sensorWrapper != null) {
        dataCount += sensorWrapper.getDataList().size();
        sensorWrapperList.add(sensorWrapper);
      }
    }
    if (sensorWrapperList.isEmpty()) {
      logger.debug("getDataForBorderRouterFromDB:  No data found for border router IP = " + borderRouterIp);
      return null;
    }
    // create BorderRouterWrapper
    BorderRouterWrapper borderRouterWrapper = new BorderRouterWrapper(borderRouter);
    borderRouterWrapper.setSensorWrapperList(sensorWrapperList);
    logger.debug("getDataForBorderRouterFromDB:  " + dataCount + " data entries found for border router IP = " + borderRouterIp);
    return borderRouterWrapper;
  }

  /**
   * Fetching data generated after the given time stamp for the given sensor from
   * database.
   *
   * Return all data if the input time stamp is <code>null</code>.
   *
   * Return <code>null</code> if the result size == 0.
   *
   * Return <code>null</code> if the input sensorIp doesn't exist in database.
   *
   * @param sensorIp  he IPv6 address of the sensor you want to fetch from.
   * @param timeStamp The earliest time stamp.
   * @return An instance of <code>SensorWrapper</code> which contains a list of
   * <code>SensorDataEntity</code>.
   */
  public SensorWrapper getDataForSensorFromDB(String sensorIp, Date timeStamp) {
    SensorEntity sensorEntity = sensorRepository.get(sensorIp);
    if (sensorEntity == null) {
      logger.error("getDataForSensorFromDB:  The input sensor IP is not exists");
      return null;
    }
    List<SensorDataEntity> dataList;
    if (timeStamp == null) {
      dataList = sensorDataRepository.getAll(sensorIp);
    } else {
      dataList = sensorDataRepository.getAllLaterThan(sensorIp, timeStamp);
    }
    if (dataList.isEmpty()) {
      logger.debug("getDataForSensorFromDB:  result size == 0");
      return null;
    }
    SensorWrapper sensorWrapper = new SensorWrapper(sensorEntity);
    sensorWrapper.setDataList(dataList);
    logger.debug("getDataForSensorFromDB:  Get data entries for sensor IP = " + sensorIp + " after " + timeStamp);
    for (SensorDataEntity data : sensorWrapper.getDataList()) {
      logger.debug("getDataForSensorFromDB:  data entity: " + data.toString());
    }
    return sensorWrapper;
  }

  /**
   * Fetching all data generated after the given time stamp from database. If the
   * input time stamp is <code>null</code>, return all data.
   *
   * Return <code>null</code> if the size of result is 0.
   *
   * @param timeStamp The earliest time stamp.
   * @return An instance of <code>DataPackage</code> which contains all data.
   */
  public DataPackage getAllDataFromDB(Date timeStamp) {
    // get all border router first
    List<BorderRouterEntity> borderRouterList = borderRouterRepository.getAll();
    // for each border router, get its BorderRouterWrapper, store them in list
    List<BorderRouterWrapper> borderRouterWrapperList = new ArrayList<>();
    for (BorderRouterEntity borderRouter : borderRouterList) {
      BorderRouterWrapper borderRouterWrapper = getDataForBorderRouterFromDB(borderRouter.getBorderRouterIp(), timeStamp);
      if (borderRouterWrapper != null) {
        borderRouterWrapperList.add(borderRouterWrapper);
      }
    }
    if (borderRouterWrapperList.isEmpty()) {
      logger.debug("getAllDataFromDB:  No data found after timeStamp = " + timeStamp);
      return null;
    } else {
      DataPackage dataPackage = new DataPackage(borderRouterWrapperList);
      logger.debug("getAllDataFromDB:  The total number of data entries is: " + dataPackage.getSize());
      return dataPackage;
    }
  }
}
