package com.twl.xg.service.impl;

import com.twl.xg.domain.BorderRouterEntity;
import com.twl.xg.domain.BorderRouterWrapper;
import com.twl.xg.domain.DataPackage;
import com.twl.xg.domain.SensorDataEntity;
import com.twl.xg.service.AccessSensorService;
import org.springframework.stereotype.Service;


@Service
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class AccessSensorServiceImpl implements AccessSensorService {

  /**
   * Fetch data from each sensor, map the data to well formatted Java Object.
   *
   * @return A list of <code>BorderRouterWrapper</code>
   */
  @Override
  public DataPackage getAllCurrentSensorData() {
    return null;
  }

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

  /**
   * Fetch current data from the input sensor IP, store the data into database.
   *
   * @param sensorIp The IPv6 address of the sensor you want to fetch from.
   * @return A <code>SensorDataEntity</code> object contains the data.
   */
  @Override
  public SensorDataEntity saveDataFromSensor(String sensorIp) {
    return null;
  }

  // TODO

  /**
   * Get current data from sensors connected to the input border router.
   *
   * @param borderRouter The instance of border router you want to fetch data from.
   * @return An instance of <code>BorderRouterWrapper</code> that contains data.
   */
  @Override
  public BorderRouterWrapper getDataFromSensorForBorderRouter(BorderRouterEntity borderRouter) {
    return null;
  }
}
