package com.twl.xg.service;

import com.twl.xg.domain.BorderRouterWrapper;
import com.twl.xg.domain.SensorDataEntity;
import com.twl.xg.domain.SensorEntity;

import java.util.List;

/**
 * This interface defines the basic operations to communicate with sensors.
 */
public interface AccessSensorService {
  /**
   * Fetch data from each sensor, map the data to well formatted Java Object.
   * @return A list of <code>BorderRouterWrapper</code>
   */
  List<BorderRouterWrapper> getAllCurrentSensorData();

  /**
   * For the input sensorEntity, get current data from the sensor, map the data
   * to a <code>SensorDataEntity</code> object.
   * @param sensorIp The IPv6 address of the sensor you want to fetch from.
   * @return A <code>SensorDataEntity</code> object contains the data.
   */
  SensorDataEntity getDataFromSensor(String sensorIp);
}
