package com.twl.xg.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twl.xg.dao.BorderRouterRepository;
import com.twl.xg.dao.SensorDataRepository;
import com.twl.xg.dao.SensorRepository;
import com.twl.xg.domain.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * This interface defines the basic operations to communicate with sensors.
 */
public abstract class AbstractAccessSensorService {
  @Autowired
  protected SensorRepository sensorRepository;
  @Autowired
  protected SensorDataRepository sensorDataRepository;
  @Autowired
  protected ApplicationContext context;
  @Autowired
  protected BorderRouterRepository borderRouterRepository;

  private static final Logger logger = Logger.getLogger(AbstractAccessSensorService.class);

  /**
   * Fetch data from each sensor, map the data to well formatted Java Object.
   * If there is no sensor in database, return null.
   *
   * @return A list of <code>BorderRouterWrapper</code>
   */
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
   * For the input sensor IP, get current data from the sensor, map the data
   * to a <code>SensorDataEntity</code> object. For the data types, use the global
   * <code>dataTypeList</code> bean to decide what kind of data to fetch.
   *
   * @param sensorIp The IPv6 address of the sensor you want to fetch from.
   * @return A <code>SensorDataEntity</code> object contains the data.
   */
  public abstract SensorDataEntity getDataFromSensor(String sensorIp) throws JsonProcessingException;

  /**
   * Fetch current data from the input sensor IP, store the data into database.
   *
   * @param sensorIp The IPv6 address of the sensor you want to fetch from.
   * @return A <code>SensorDataEntity</code> object contains the data.
   */
  @Transactional
  public SensorDataEntity saveDataFromSensor(String sensorIp) throws JsonProcessingException {
    SensorDataEntity sensorDataEntity = getDataFromSensor(sensorIp);
    sensorDataRepository.save(sensorDataEntity);
    logger.debug("saveDataFromSensor:  Saved sensor data --> " + sensorDataEntity);
    return sensorDataEntity;
  }

  /**
   * Get current data from sensors connected to the input border router.
   *
   * @throws NullPointerException if the input borderRouter entity is null.
   *
   * @param borderRouter The instance of border router you want to fetch data from.
   * @return An instance of <code>BorderRouterWrapper</code> that contains data.
   */
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
   * Save all current data in database.
   *
   * @return <code>true</code> if there are sensors in database, return <code>false</code> if there is no  sensor in database.
   * @throws JsonProcessingException the json processing exception
   */
  @Transactional
  public boolean saveAllCurrentDataFromSensor() throws JsonProcessingException {
    // get all sensor IP first
    List<String> sensorIpList = sensorRepository.getAllSensorIp();
    if (sensorIpList.isEmpty()) {
      return false;
    }
    // get sensor data from each sensor IP, store them in database
    for (String sensorIp : sensorIpList) {
      saveDataFromSensor(sensorIp);
    }
    return true;
  }
}
