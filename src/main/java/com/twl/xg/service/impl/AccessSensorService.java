package com.twl.xg.service.impl;

import com.twl.xg.domain.SensorDataEntity;
import com.twl.xg.service.AbstractAccessSensorService;
import org.springframework.stereotype.Service;

/**
 * Real implementation of <code>getDataFromSensor</code>. Because of some technical
 * reasons, we cannot provide a complete implementation for now.
 *
 * @see AbstractAccessSensorService
 * @author Xiaozheng Guo
 * @version 1.0
 */
@Service
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class AccessSensorService extends AbstractAccessSensorService {

  /**
   * For the input sensorEntity, get current data from the sensor, map the data
   * to a <code>SensorDataEntity</code> object.
   *
   * @param sensorIp The IPv6 address of the sensor you want to fetch from.
   * @return A <code>SensorDataEntity</code> object contains the data.
   */
  @Override
  public SensorDataEntity getDataFromSensor(String sensorIp) {
    return null;
  }
}
