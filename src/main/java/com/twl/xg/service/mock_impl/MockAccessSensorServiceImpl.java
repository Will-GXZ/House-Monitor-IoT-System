package com.twl.xg.service.mock_impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twl.xg.dao.BorderRouterRepository;
import com.twl.xg.dao.SensorDataRepository;
import com.twl.xg.dao.SensorRepository;
import com.twl.xg.domain.*;
import com.twl.xg.service.AccessSensorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class MockAccessSensorServiceImpl implements AccessSensorService {
  @Autowired
  SensorRepository sensorRepository;
  @Autowired
  SensorDataRepository sensorDataRepository;
  @Autowired
  ApplicationContext context;
  @Autowired
  BorderRouterRepository borderRouterRepository;

  private static final Logger logger = Logger.getLogger(MockAccessSensorServiceImpl.class);

  /**
   * Fetch data from each sensor, map the data to well formatted Java Object.
   * If there is not sensor in database, return null.
   *
   * @return A list of <code>BorderRouterWrapper</code>
   */
  @Override
  @Transactional
  public DataPackage getAllCurrentSensorData() throws JsonProcessingException {
    if (sensorRepository.size() == 0) {
      logger.error("getAllCurrentSensorData:  No sensor found");
      return null;
    }
    // get all border router first
    List<BorderRouterEntity> borderRouterList = borderRouterRepository.getAll();
    // for each border router get its border router wrapper
    List<BorderRouterWrapper> borderRouterWrapperList = new ArrayList<>();
    for (BorderRouterEntity borderRouter : borderRouterList) {
      BorderRouterWrapper borderRouterWrapper = getDataFromSensorForBorderRouter(borderRouter);
      borderRouterWrapperList.add(borderRouterWrapper);
    }
    // create DataPackage object
    DataPackage dataPackage = new DataPackage(borderRouterWrapperList);
    logger.debug("getAllCurrentSensorData:  data package --> " + dataPackage);
    return dataPackage;
  }

  /**
   * Get current data from sensors connected to the input border router.
   *
   * @throws NullPointerException if the input borderRouter entity is null.
   *
   * @param borderRouter The instance of border router you want to fetch data from.
   * @return An instance of <code>BorderRouterWrapper</code> that contains data.
   */
  @Override
  @Transactional
  public BorderRouterWrapper getDataFromSensorForBorderRouter(BorderRouterEntity borderRouter) throws JsonProcessingException {
    if (borderRouter == null) {
      throw(new NullPointerException("The input border router entity is null"));
    }
    // create border router wrapper
    BorderRouterWrapper borderRouterWrapper = new BorderRouterWrapper(borderRouter);
    // get all sensor for the input border router
    List<SensorEntity> sensorList = sensorRepository.getAll(borderRouter.getBorderRouterIp());
    // create sensor wrapper for each sensor
    List<SensorWrapper> sensorWrapperList = new ArrayList<>();
    for (SensorEntity sensor : sensorList) {
      SensorWrapper sensorWrapper = new SensorWrapper(sensor);
      List<SensorDataEntity> dataList = new ArrayList<>();
      SensorDataEntity sensorData = getDataFromSensor(sensor.getSensorIp());
      dataList.add(sensorData);
      sensorWrapper.setDataList(dataList);
      if (! dataList.isEmpty()) {
        sensorWrapperList.add(sensorWrapper);
      }
    }
    borderRouterWrapper.setSensorWrapperList(sensorWrapperList);
    logger.debug("getDataFromSensorForBorderRouter:  borderRouterWrapper --> " + new ObjectMapper().writeValueAsString(borderRouterWrapper));
    return borderRouterWrapper;
  }

  /**
   * For the input sensorEntity, get current data from the sensor, map the data
   * to a <code>SensorDataEntity</code> object. This is just a dummy implementation,
   * generate random data for each sensor IP.
   *
   * @throws NullPointerException If the input sensor IP does not exist in the database.
   * @throws NoSuchElementException if  <code>dataTypeList.size() == 0</code>.
   *
   * @param sensorIp The IPv6 address of the sensor you want to fetch from.
   * @return A <code>SensorDataEntity</code> object contains the data.
   */
  @Override
  @Transactional
  public SensorDataEntity getDataFromSensor(String sensorIp) throws JsonProcessingException {
    // get SensorEntity first
    SensorEntity sensor = sensorRepository.get(sensorIp);
    if (sensor == null) {
      throw(new NullPointerException("The input sensorIp doesn't exist in the database"));
    }

    // get dataTypeList
    List<String> dataTypeList = (List<String>)context.getBean("dataTypeList");
    if (dataTypeList.isEmpty()) {
      throw(new NoSuchElementException("dataTypeList bean has not been initialized"));
    }

    // generate random data, convert to json
    Map<String, String> dataMap = new TreeMap<>();
    for (String dataType : dataTypeList) {
      dataMap.put(dataType, "" + (Math.random() * 200 + 25) / 10.0);
    }

    ObjectMapper mapper = new ObjectMapper();
    String dataJson = mapper.writeValueAsString(dataMap);

    // create sensor data entity
    SensorDataEntity sensorData = new SensorDataEntity();
    sensorData.setSensorIp(sensorIp);
    sensorData.setSensorBySensorIp(sensor);
    sensorData.setDataJson(dataJson);

    logger.debug("getDataFromSensor:  For sensor IP = " + sensorIp + " , " + sensorData);
    return sensorData;
  }

  /**
   * Fetch current data from the input sensor IP, store the data into database.
   *
   * @param sensorIp The IPv6 address of the sensor you want to fetch from.
   * @return A <code>SensorDataEntity</code> object contains the data.
   */
  @Override
  @Transactional
  public SensorDataEntity saveDataFromSensor(String sensorIp) throws JsonProcessingException {
    SensorDataEntity sensorDataEntity = getDataFromSensor(sensorIp);
    sensorDataRepository.save(sensorDataEntity);
    logger.debug("saveDataFromSensor:  Saved sensor data --> " + sensorDataEntity);
    return sensorDataEntity;
  }
}
