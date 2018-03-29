package com.twl.xg.service;

import com.twl.xg.domain.BorderRouterWrapper;
import com.twl.xg.domain.SensorWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * This interface defines the basic operations to fetching, processing and mapping
 * data from database.
 */
public interface DataFetchingAndMappingService {
  /**
   * Update <code>dataTypeList</code> according to the input array. Every time the
   * <code>dataTypeList</code> gets updated, the <code>sensor_data</code> table
   * in database will be cleared. Because the number of data fields have been
   * changed, and it will be inconsistent.
   * Return value is the current <code>dataTypeList</code>, return <code>null</code>
   * if the input is invalid.
   * @param dataTypeArray An array of data types string
   * @return Return a list of String which contains all data types name set by user.
   */
  List<String> SetDataTypes(String[] dataTypeArray);

  /**
   * Fetching data from database for given border router.
   * @param borderRouterIp The IPv6 address of the border router you want to
   *                       fetch date from.
   * @return An instance of <code>BorderRouterWrapper</code> that contains a list
   * of <code>SensorDataEntity</code>.
   */
  BorderRouterWrapper getDataForBorderRouterFromDB(String borderRouterIp);

  /**
   * Fetching data from database for given border router. Return all the data that
   * generated later than the given timeStamp;
   * @param borderRouterIp The IPv6 address of the border router you want to
   *                       fetch date from.
   * @param timeStamp The earliest time stamp.
   * @return An instance of <code>BorderRouterWrapper</code> that contains a list
   * of <code>SensorDataEntity</code>.
   */
  BorderRouterWrapper getDataForBorderRouterFromDB(String borderRouterIp, Date timeStamp);

  /**
   * Fetching all data from database, mapping them to Java objects.
   * @return A list of <code>BorderRouterWrapper</code>.
   */
  List<BorderRouterWrapper> getAllDataFromDB();

  /**
   * Fetching all data generated after the given time stamp from database.
   * @param timeStamp The earliest time stamp.
   * @return A list of <code>BorderRouterWrapper</code>.
   */
  List<BorderRouterWrapper> getAllDataFromDB(Date timeStamp);

  /**
   * Fetching data of the given sensor from database.
   * @param sensorIp The IPv6 address of the sensor you want to fetch from.
   * @return An instance of <code>SensorWrapper</code> which contains a list of
   * <code>SensorDataEntity</code>.
   */
  SensorWrapper getDateForSensorFromDB(String sensorIp);

  /**
   * Fetching data generated after the given time stamp for the given sensor from
   * database.
   * @param sensorIp he IPv6 address of the sensor you want to fetch from.
   * @param timeStamp The earliest time stamp.
   * @return An instance of <code>SensorWrapper</code> which contains a list of
   * <code>SensorDataEntity</code>.
   */
  SensorWrapper getDataForSensorFromDB(String sensorIp, Date timeStamp);
}
