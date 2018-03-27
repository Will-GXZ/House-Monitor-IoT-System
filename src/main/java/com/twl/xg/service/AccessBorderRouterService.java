package com.twl.xg.service;

import java.util.List;

/**
 * This interface defines the basic operations to communicate with border routers.
 */
public interface AccessBorderRouterService {
  /**
   * Take as input a <code>String</code> of borderRouterIp, return a list of
   * IPv6 address of sensors that are connected to the given border router.
   * If the input borderRouterIp is inValid, return null instead.
   * @param borderRouterIp The IPv6 address of border router you want to query.
   * @return A list of sensor IPv6 address.
   */
  List<String> getSensorIpByBorderRouterIp(String borderRouterIp);


  /**
   * Save the input border router in database.
   * @param borderRouterIp The IPv6 address of the border router you want to save.
   * @param borderRouterName The name you want to set for the border router.
   */
  void saveBorderRouter(String borderRouterIp, String borderRouterName);

  /**
   * Save the sensors connected to the input border router in database. For the parameter
   * <code>sensorIpList</code>, call <code>getSensorIpByBorderRouterIp</code> to get it.
   * @param borderRouterIp The IPv6 address of the border router that the list of sensors are
   *                       connnected.
   * @param sensorIpList The list of IPv6 address of sensors that are connected to the input
   *                     border router.
   */
  void saveSensorsForBorderRouterIp(String borderRouterIp, List<String> sensorIpList);
}
