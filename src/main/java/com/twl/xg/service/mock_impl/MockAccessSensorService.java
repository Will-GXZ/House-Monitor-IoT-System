package com.twl.xg.service.mock_impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twl.xg.dao.SensorDataRepository;
import com.twl.xg.dao.SensorRepository;
import com.twl.xg.domain.DataPackage;
import com.twl.xg.domain.SensorDataEntity;
import com.twl.xg.domain.SensorEntity;
import com.twl.xg.service.AccessSensorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.TreeMap;

@Service
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class MockAccessSensorService implements AccessSensorService {
  @Autowired
  SensorRepository sensorRepository;
  @Autowired
  SensorDataRepository sensorDataRepository;

  private static final Logger logger = Logger.getLogger(MockAccessSensorService.class);

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
   * to a <code>SensorDataEntity</code> object. This is just a dummy implementation,
   * generate random data for each sensor IP.
   *
   * @throws NullPointerException If the input sensor IP does not exist in the database.
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
      throw(new NullPointerException("The input sensorIp doesn't exist in the database"));
    }
    // generate random data, convert to json
    Map<String, String> dataMap = new TreeMap<>();
    dataMap.put("temperature", "" + (Math.random() * 20 + 5) / 10.0);
    dataMap.put("humidity", "" + (Math.random() * 20 + 5) / 10.0);
    dataMap.put("lightness", "" + (Math.random() * 20 + 5) / 10.0);

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

  /**
   * Fetch current data from the input sensor IP, store the data into database.
   *
   * @param sensorIp The IPv6 address of the sensor you want to fetch from.
   * @return A <code>SensorDataEntity</code> object contains the data.
   */
  @Override
  public SensorDataEntity saveDataFromSensor(String sensorIp) throws JsonProcessingException {
    SensorDataEntity sensorDataEntity = getDataFromSensor(sensorIp);
    sensorDataRepository.save(sensorDataEntity);
    logger.debug("saveDataFromSensor:  Saved sensor data --> " + sensorDataEntity);
    return sensorDataEntity;
  }
}
