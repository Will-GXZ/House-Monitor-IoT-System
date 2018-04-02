package com.twl.xg.service;

import com.twl.xg.dao.BorderRouterRepository;
import com.twl.xg.dao.SensorRepository;
import com.twl.xg.domain.BorderRouterEntity;
import com.twl.xg.domain.SensorEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This abstract defines the basic operations to communicate with border routers.
 * Also provide some common implementations.
 */
public abstract class AbstractAccessBorderRouterService {
  @Autowired
  protected BorderRouterRepository borderRouterRepository;
  @Autowired
  protected SensorRepository sensorRepository;

  private static final Logger logger = Logger.getLogger(AbstractAccessBorderRouterService.class);

  /**
   * Take as input a <code>String</code> of borderRouterIp, return a list of
   * IPv6 address of sensors that are connected to the given border router.
   *
   * If the input borderRouterIp is inValid, return null instead.
   *
   * If there is no sensor connected to the border router, return an empty list.
   *
   * @param borderRouterIp The IPv6 address of border router you want to query.
   * @return A list of sensor IPv6 address.
   */
  public abstract List<String> getSensorIpByBorderRouterIp(String borderRouterIp);


  /**
   * Save the input border router in database. If the input <code>borderRouterName</code>
   * is <code>null</code>, set its IP as name.
   *
   * @param borderRouterIp   The IPv6 address of the border router you want to save.
   * @param borderRouterName The name you want to set for the border router.
   * @return An instance of <code>BorderRouterEntity</code> for the input border router.
   */
  @Transactional
  public BorderRouterEntity saveBorderRouter(String borderRouterIp, String borderRouterName) {
    BorderRouterEntity borderRouterEntity = new BorderRouterEntity();
    borderRouterEntity.setBorderRouterIp(borderRouterIp);
    if (borderRouterName == null) {
      borderRouterEntity.setBorderRouterName(borderRouterIp);
    } else {
      borderRouterEntity.setBorderRouterName(borderRouterName);
    }
    borderRouterRepository.save(borderRouterEntity);
    logger.debug("saveBorderRouter:  Saved a border router, ip = " + borderRouterIp + ", name = " + borderRouterName);
    return borderRouterEntity;
  }

  /**
   * Save the sensors connected to the input border router in database.
   * The default sensor name is the same to the sensor's IP.
   *
   * @throws NullPointerException if the input border router IP doesn't not exist.
   *
   * @param borderRouterIp The IPv6 address of the border router that the list of sensors are
   *                       connnected.
   */
  @Transactional
  public void saveSensorsForBorderRouterIp(String borderRouterIp) {
    // get border router first
    BorderRouterEntity borderRouter = borderRouterRepository.get(borderRouterIp);
    if (borderRouter == null) {
      throw(new NullPointerException("The input borderRouterIp doesn't exist in the database"));
    }
    // get sensor ip list
    List<String> sensorIpList = getSensorIpByBorderRouterIp(borderRouterIp);
    // create Sensor for each sensor IP, store them in database
    for (String sensorIp : sensorIpList) {
      SensorEntity sensorEntity = new SensorEntity();
      sensorEntity.setSensorName(sensorIp);
      sensorEntity.setSensorIp(sensorIp);
      sensorEntity.setBorderRouterByBorderRouterIp(borderRouter);
      sensorRepository.save(sensorEntity);
    }
    logger.debug("saveSensorForBorderRouterIp:  Saved " + sensorIpList.size() + " sensors for border router IP = " + borderRouterIp);
  }

  /**
   * Check if a border router IP is exist, notice that this method doesn't check
   * if the IP is in database, it checks if the router IP exists in the real network.
   *
   * @param borderRouterIp The IPv6 address of the border router you want to check.
   * @return <code>true</code> for border router exists, <code>false</code> otherwise.
   */
  public abstract boolean existBorderRouter(String borderRouterIp);
}
