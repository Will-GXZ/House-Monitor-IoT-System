package com.twl.xg.service.mock_impl;

import com.twl.xg.dao.BorderRouterRepository;
import com.twl.xg.dao.SensorRepository;
import com.twl.xg.domain.BorderRouterEntity;
import com.twl.xg.domain.SensorEntity;
import com.twl.xg.service.AccessBorderRouterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class MockAccessBorderRouterServiceImpl implements AccessBorderRouterService {
  @Autowired
  BorderRouterRepository borderRouterRepository;
  @Autowired
  SensorRepository sensorRepository;

  private static final Logger logger = Logger.getLogger(MockAccessBorderRouterServiceImpl.class);

  /**
   * Check if a border router IP is exist. In this dummy implementation, just return true.
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

  /**
   * Save the input border router in database. If the input <code>borderRouterName</code>
   * is <code>null</code>, set its IP as name.
   *
   * @param borderRouterIp   The IPv6 address of the border router you want to save.
   * @param borderRouterName The name you want to set for the border router.
   * @return An instance of <code>BorderRouterEntity</code> for the input border router.
   */
  @Override
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
  @Override
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
}
