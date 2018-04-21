package com.twl.xg.service.mock_impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twl.xg.domain.*;
import com.twl.xg.service.AbstractAccessSensorService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class MockAccessSensorService extends AbstractAccessSensorService {

  private static final Logger logger = Logger.getLogger(MockAccessSensorService.class);

  /**
   * For the input sensorEntity, get current data from the sensor, map the data
   * to a <code>SensorDataEntity</code> object. If a data cannot be acquired, store
   * Integer.MAX_VALUE for it.
   *
   * This is just a dummy implementation, generate random data for each sensor IP.
   * Each type of data has a probability of 0.2 to be Integer.MAX_VALUE.
   *
   * @throws NullPointerException If the input sensor IP does not exist in the database.
   * @throws RuntimeException if  <code>dataTypeList.size() == 0</code>.
   *
   * @param sensorIp The IPv6 address of the sensor you want to fetch from.
   * @return A <code>SensorDataEntity</code> object contains the data.
   */
  @Override
  @Transactional
  public SensorDataEntity getDataFromSensor(String sensorIp) throws JsonProcessingException {
    // get SensorEntity first
    SensorEntity sensor = sensorRepository.get(sensorIp);
    if (sensor == null) {
      throw new NullPointerException("The input sensorIp doesn't exist in the database");
    }

    // get dataTypeList
    List<String> dataTypeList = (List<String>)context.getBean("dataTypeList");
    if (dataTypeList.isEmpty()) {
      logger.error("getDataFromSensor: dataTypeList bean has not been initialized");
      throw new RuntimeException("dataTypeList bean has not been initialized");
    }

    // generate random data, convert to json
    Map<String, String> dataMap = new TreeMap<>();
    for (String dataType : dataTypeList) {
      double data;
      int randomNum = (int) (Math.random() * 10);
      if (randomNum < 2) {
        data = Integer.MAX_VALUE;
      } else {
        data = ((Math.random() * 100 + 150) / 10.0);
      }
      dataMap.put(dataType, String.format("%7f", data));
    }

    ObjectMapper mapper = new ObjectMapper();
    String dataJson = mapper.writeValueAsString(dataMap);

    // create sensor data entity
    SensorDataEntity sensorData = new SensorDataEntity();
    sensorData.setSensorIp(sensorIp);
    sensorData.setSensorBySensorIp(sensor);
    sensorData.setDataJson(dataJson);

    logger.debug("getDataFromSensor:  For sensor IP = " + sensorIp + " , " + sensorData);
    return sensorData;
  }
}
