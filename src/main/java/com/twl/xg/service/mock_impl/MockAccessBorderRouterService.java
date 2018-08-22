package com.twl.xg.service.mock_impl;

import com.twl.xg.service.AbstractAccessBorderRouterService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy implementation of <code>existBorderRouter()</code> and <code>getSensorIpByBorderRouterIp()</code>
 *
 * @author Xiaozheng Guo
 * @version 1.0
 */
@Service
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class MockAccessBorderRouterService extends AbstractAccessBorderRouterService {

  private static final Logger logger = Logger.getLogger(MockAccessBorderRouterService.class);

  /**
   * Check if a border router IP is exist, notice that this method doesn't check
   * if the IP is in database, it checks if the router IP exists in the real network.
   *
   * In this dummy implementation, return true for the probability of 0.8, return false
   * otherwise.
   *
   * @param borderRouterIp The IPv6 address of the border router you want to check.
   * @return <code>true</code> for border router exists, <code>false</code> otherwise.
   */
  @Override
  public boolean existBorderRouter(String borderRouterIp) {
    int randNum = (int) (Math.random() * 10);
    if (randNum <= 9) {
      return true;
    } else {
      logger.error("existBorderRouter:  borderRouterIP --> " + borderRouterIp + ", doesn't exist !");
      return false;
    }
  }

  /**
   * Take as input a <code>String</code> of borderRouterIp, return a list of
   * IPv6 address of sensors that are connected to the given border router.
   * If the input borderRouterIp is inValid, return null instead.
   *
   * This method just create dummy sensor ip for each input borde router. The
   * number of sensor will be a random number from 0 to 6.
   *
   * @param borderRouterIp The IPv6 address of border router you want to query.
   * @return A list of sensor IPv6 address.
   */
  @Override
  public List<String> getSensorIpByBorderRouterIp(String borderRouterIp) {
    // create dummy sensor ip for each input borde router ip,
    List<String> sensorIpList = new ArrayList<>();
    int sensorNum = (int) (Math.random() * 10);
    for (int i = 0; i < sensorNum; ++i) {
      sensorIpList.add(borderRouterIp + "--sensor-" + i);
      logger.debug("getSensorIpByBorderRouterIp:  Generated snesor IP = " + sensorIpList.get(sensorIpList.size() - 1));
    }
    return sensorIpList;
  }
}
