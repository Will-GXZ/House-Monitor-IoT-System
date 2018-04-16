package com.twl.xg.service.mock_impl;

import com.twl.xg.dao.BorderRouterRepository;
import com.twl.xg.dao.SensorRepository;
import com.twl.xg.service.AbstractAccessBorderRouterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class MockAccessBorderRouterService extends AbstractAccessBorderRouterService {
  @Autowired
  BorderRouterRepository borderRouterRepository;
  @Autowired
  SensorRepository sensorRepository;

  private static final Logger logger = Logger.getLogger(MockAccessBorderRouterService.class);

  /**
   * Check if a border router IP is exist, notice that this method doesn't check
   * if the IP is in database, it checks if the router IP exists in the real network.
   *
   * In this dummy implementation, just return true.
   *
   * @param borderRouterIp The IPv6 address of the border router you want to check.
   * @return <code>true</code> for border router exists, <code>false</code> otherwise.
   */
  @Override
  public boolean existBorderRouter(String borderRouterIp) {
    return true;
  }

  /**
   * Take as input a <code>String</code> of borderRouterIp, return a list of
   * IPv6 address of sensors that are connected to the given border router.
   * If the input borderRouterIp is inValid, return null instead.
   *
   * This method just create dummy sensor ip for each input borde router.
   *
   * @param borderRouterIp The IPv6 address of border router you want to query.
   * @return A list of sensor IPv6 address.
   */
  @Override
  public List<String> getSensorIpByBorderRouterIp(String borderRouterIp) {
    // create dummy sensor ip for each input borde router ip,
    List<String> sensorIpList = new ArrayList<>();
    for (int i = 1; i <= 3; ++i) {
      sensorIpList.add(borderRouterIp + "--sensor " + i);
      logger.debug("getSensorIpByBorderRouterIp:  Generated snesor IP = " + sensorIpList.get(sensorIpList.size() - 1));
    }
    return sensorIpList;
  }


}
