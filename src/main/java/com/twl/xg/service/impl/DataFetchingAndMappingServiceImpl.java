package com.twl.xg.service.impl;

import com.twl.xg.domain.BorderRouterWrapper;
import com.twl.xg.domain.SensorWrapper;
import com.twl.xg.service.DataFetchingAndMappingService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class DataFetchingAndMappingServiceImpl implements DataFetchingAndMappingService {
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
