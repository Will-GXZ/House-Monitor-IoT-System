package com.twl.xg.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twl.xg.domain.BorderRouterWrapper;
import com.twl.xg.domain.DataPackage;
import com.twl.xg.domain.SensorDataEntity;
import com.twl.xg.domain.SensorEntity;

import java.util.List;

/**
 * This interface defines the basic operations to communicate with sensors.
 */
public interface AccessSensorService {
  /**
   * Fetch data from each sensor, map the data to well formatted Java Object.
   *
   * @return An instance of <code>DataPackage</code> that contains all current data.
   */
  DataPackage getAllCurrentSensorData();

  /**
   * For the input sensor IP, get current data from the sensor, map the data
   * to a <code>SensorDataEntity</code> object. For the data types, use the global
   * <code>dataTypeList</code> bean to decide what kind of data to fetch.
   *
   * @param sensorIp The IPv6 address of the sensor you want to fetch from.
   * @return A <code>SensorDataEntity</code> object contains the data.
   */
  SensorDataEntity getDataFromSensor(String sensorIp) throws JsonProcessingException;

  /**
   * Fetch current data from the input sensor IP, store the data into database.
   * @param sensorIp The IPv6 address of the sensor you want to fetch from.
   * @return A <code>SensorDataEntity</code> object contains the data.
   */
  SensorDataEntity saveDataFromSensor(String sensorIp) throws JsonProcessingException;
}
