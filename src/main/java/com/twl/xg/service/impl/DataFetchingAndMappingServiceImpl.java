package com.twl.xg.service.impl;

import com.twl.xg.dao.SensorDataRepository;
import com.twl.xg.domain.BorderRouterWrapper;
import com.twl.xg.domain.SensorWrapper;
import com.twl.xg.service.DataFetchingAndMappingService;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class DataFetchingAndMappingServiceImpl implements DataFetchingAndMappingService {
  @Autowired
  ApplicationContext context;

  @Autowired
  SessionFactory sessionFactory;

  @Autowired
  SensorDataRepository sensorDataRepository;

  private static final Logger logger = Logger.getLogger(DataFetchingAndMappingServiceImpl.class);

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
  @Override
  @Transactional
  public synchronized List<String> SetDataTypes(String[] dataTypeArray) {
    if (dataTypeArray == null || dataTypeArray.length == 0) {
      logger.error("SetDataTypes: Invalid input");
      return null;
    }
    // check if the input data types are the same to the current data types.
    List<String> currentDataTypeList = (List<String>)context.getBean("dataTypeList");
    Set<String> currentDataTypeSet = new HashSet<>(currentDataTypeList);
    Set<String> inputDataTypeSet = new HashSet<>(Arrays.asList(dataTypeArray));
    if (currentDataTypeSet.equals(inputDataTypeSet)) {
      // if same, do nothing, return current dataTypeList
      logger.debug("SetDataTypes: Input data types are the same to current data types");
      return currentDataTypeList;
    } else {
      // else, clear sensor_data table, update currentDataTypeList, and return it
      sensorDataRepository.clear();
      currentDataTypeList.clear();
      currentDataTypeList.addAll(inputDataTypeSet);
      Collections.sort(currentDataTypeList);
      logger.debug("SetDataTypes: dataTypeList updated to: " + currentDataTypeList.toString());
      return currentDataTypeList;
    }
  }

  /**
   * Fetching data from database for given border router.
   *
   * @param borderRouterIp The IPv6 address of the border router you want to
   *                       fetch date from.
   * @return An instance of <code>BorderRouterWrapper</code> that contains a list
   * of <code>SensorDataEntity</code>.
   */
  @Override
  public BorderRouterWrapper getDataForBorderRouterFromDB(String borderRouterIp) {
    return null;
  }

  /**
   * Fetching data from database for given border router. Return all the data that
   * generated later than the given timeStamp;
   *
   * @param borderRouterIp The IPv6 address of the border router you want to
   *                       fetch date from.
   * @param timeStamp      The earliest time stamp.
   * @return An instance of <code>BorderRouterWrapper</code> that contains a list
   * of <code>SensorDataEntity</code>.
   */
  @Override
  public BorderRouterWrapper getDataForBorderRouterFromDB(String borderRouterIp, Date timeStamp) {
    return null;
  }

  /**
   * Fetching all data from database, mapping them to Java objects.
   *
   * @return A list of <code>BorderRouterWrapper</code>.
   */
  @Override
  public List<BorderRouterWrapper> getAllDataFromDB() {
    return null;
  }

  /**
   * Fetching all data generated after the given time stamp from database.
   *
   * @param timeStamp The earliest time stamp.
   * @return A list of <code>BorderRouterWrapper</code>.
   */
  @Override
  public List<BorderRouterWrapper> getAllDataFromDB(Date timeStamp) {
    return null;
  }

  /**
   * Fetching data of the given sensor from database.
   *
   * @param sensorIp The IPv6 address of the sensor you want to fetch from.
   * @return An instance of <code>SensorWrapper</code> which contains a list of
   * <code>SensorDataEntity</code>.
   */
  @Override
  public SensorWrapper getDateForSensorFromDB(String sensorIp) {
    return null;
  }

  /**
   * Fetching data generated after the given time stamp for the given sensor from
   * database.
   *
   * @param sensorIp  he IPv6 address of the sensor you want to fetch from.
   * @param timeStamp The earliest time stamp.
   * @return An instance of <code>SensorWrapper</code> which contains a list of
   * <code>SensorDataEntity</code>.
   */
  @Override
  public SensorWrapper getDataForSensorFromDB(String sensorIp, Date timeStamp) {
    return null;
  }
  // TODO
}
