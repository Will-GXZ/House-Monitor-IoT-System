package com.twl.xg.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twl.xg.config.AppConfig;
import com.twl.xg.config.HibernateConfig;
import com.twl.xg.config.ServletInitializer;
import com.twl.xg.domain.SensorDataEntity;
import com.twl.xg.domain.SensorEntity;
import com.twl.xg.service.AccessSensorService;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServletInitializer.class, AppConfig.class, HibernateConfig.class})
@WebAppConfiguration
public class TestMockAccessSensorService {
  @Autowired
  @Qualifier(value = "mockAccessSensorService")
  AccessSensorService accessSensorService;

  @Autowired
  SessionFactory sessionFactory;

  private static final Logger logger = Logger.getLogger(TestMockAccessSensorService.class);

  @Test
  @Transactional
  public void testGetDataFromSensor() throws JsonProcessingException {
    // test non-exist sensor IP
    try {
      accessSensorService.getDataFromSensor("NonExistIP");
    } catch (NullPointerException e) {
      e.printStackTrace();
    }

    // test existed sensor IP
    SensorEntity sensor = (SensorEntity) sessionFactory.getCurrentSession()
                          .createQuery("FROM SensorEntity ORDER BY rand()")
                          .setMaxResults(1)
                          .uniqueResult();
    SensorDataEntity res = accessSensorService.getDataFromSensor(sensor.getSensorIp());
    if (res == null || res.getSensorIp() != sensor.getSensorIp()) {
      fail("********** testGetDataFromSensor failed ************");
    } else  {
      logger.debug("*********** testGetDataFromSensor passed ***********");
    }
  }

  @Test
  @Transactional
  public void testSaveDataFromSensor() throws JsonProcessingException {
    SensorEntity sensor = (SensorEntity) sessionFactory.getCurrentSession()
        .createQuery("FROM SensorEntity ORDER BY rand()")
        .setMaxResults(1)
        .uniqueResult();
    SensorDataEntity res = accessSensorService.saveDataFromSensor(sensor.getSensorIp());
    if (res == null || res.getSensorIp() != sensor.getSensorIp()) {
      fail("********** testGetDataFromSensor failed ************");
    } else  {
      logger.debug("*********** testGetDataFromSensor passed ***********");
    }
  }
}
