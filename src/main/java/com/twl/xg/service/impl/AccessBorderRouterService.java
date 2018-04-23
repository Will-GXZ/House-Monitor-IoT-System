package com.twl.xg.service.impl;

import com.twl.xg.service.AbstractAccessBorderRouterService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Real implementation of <code>getSensorIpByBorderRouterIp()</code> and
 * <code>existBorderRouter()</code>. Because of some technical reasons, we cannot
 * provide a complete implementation for now.
 *
 * @see AbstractAccessBorderRouterService
 * @author Xiaozheng Guo
 * @version 1.0
 */
@Service
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class AccessBorderRouterService extends AbstractAccessBorderRouterService {

  /**
   * Take as input a <code>String</code> of borderRouterIp, return a list of
   * IPv6 address of sensors that are connected to the given border router.
   * If the input borderRouterIp is inValid, return null instead.
   *
   * @param borderRouterIp The IPv6 address of border router you want to query.
   * @return A list of sensor IPv6 address.
   */
  @Override
  public List<String> getSensorIpByBorderRouterIp(String borderRouterIp) {
    return null;
  }

  /**
   * Check if a border router IP is exist.
   *
   * @param borderRouterIp The IPv6 address of the border router you want to check.
   * @return <code>true</code> for border router exists, <code>false</code> otherwise.
   */
  @Override
  public boolean existBorderRouter(String borderRouterIp) {
    return false;
  }

  // TODO
}
