package com.twl.xg.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twl.xg.config.AppConfig;
import com.twl.xg.config.HibernateConfig;
import com.twl.xg.config.ServletInitializer;
import com.twl.xg.config.TaskSchedulerConfig;
import com.twl.xg.domain.BorderRouterEntity;
import com.twl.xg.domain.SensorDataEntity;
import com.twl.xg.domain.SensorEntity;
import com.twl.xg.service.AbstractAccessSensorService;
import com.twl.xg.service.DataFetchingAndMappingService;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServletInitializer.class, AppConfig.class, HibernateConfig.class, TaskSchedulerConfig.class})
@WebAppConfiguration
public class TestMockAccessSensorService {
  @Autowired
  @Qualifier(value = "mockAccessSensorService")
  AbstractAccessSensorService abstractAccessSensorService;

  @Autowired
  DataFetchingAndMappingService dataFetchingAndMappingService;

  @Autowired
  SessionFactory sessionFactory;

  @Autowired
  ApplicationContext context;

  private static final Logger logger = Logger.getLogger(TestMockAccessSensorService.class);

  @Test
  @Transactional
  public void testGetDataFromSensorForBorderRouter() throws JsonProcessingException {
    // set up data type list
    List<String> dataTypeList = (List<String>) context.getBean("dataTypeList");
    dataTypeList.addAll(Arrays.asList("xguo", "jcq", "rlg"));

    BorderRouterEntity borderRouter = (BorderRouterEntity) sessionFactory.getCurrentSession()
                                      .createQuery("from BorderRouterEntity ORDER BY rand()")
                                      .setMaxResults(1)
                                      .uniqueResult();
    if (abstractAccessSensorService.getDataFromSensorForBorderRouter(borderRouter) == null) {
      fail("*********** testGetDataFromSensorForBorderRouter failed *************");
    } else {
      logger.debug("************** testGetDataFromSensorForBorderRouter passed ***************");
    }
  }

  @Test
  @Transactional
  public void testGetDataFromSensor() throws JsonProcessingException {
    // test non-exist sensor IP
    try {
      abstractAccessSensorService.getDataFromSensor("NonExistIP");
    } catch (NullPointerException e) {
      e.printStackTrace();
    }

    // test existed sensor IP
    SensorEntity sensor = (SensorEntity) sessionFactory.getCurrentSession()
                          .createQuery("FROM SensorEntity ORDER BY rand()")
                          .setMaxResults(1)
                          .uniqueResult();
    SensorDataEntity res = abstractAccessSensorService.getDataFromSensor(sensor.getSensorIp());
    if (res == null || res.getSensorIp() != sensor.getSensorIp()) {
      fail("********** testGetDataFromSensor failed ************");
    } else  {
      logger.debug("*********** testGetDataFromSensor passed ***********");
    }
  }

  @Test
  @Transactional
  public void testSaveDataFromSensor() throws JsonProcessingException {
    // initialize dataTypeList
    dataFetchingAndMappingService.setDataTypes(new String[] {"temp", "humidity", "xguo", "jcq"});

    SensorEntity sensor = (SensorEntity) sessionFactory.getCurrentSession()
        .createQuery("FROM SensorEntity ORDER BY rand()")
        .setMaxResults(1)
        .uniqueResult();
    SensorDataEntity res = abstractAccessSensorService.saveDataFromSensor(sensor.getSensorIp());
    if (res == null || res.getSensorIp() != sensor.getSensorIp()) {
      fail("********** testGetDataFromSensor failed ************");
    } else  {
      logger.debug("*********** testGetDataFromSensor passed ***********");
    }
  }

  @Test
  @Transactional
//  @Commit
  public void testSaveAllCurrentDataFromSensor() throws JsonProcessingException {
    dataFetchingAndMappingService.setDataTypes(new String[] {"temp", "humidity", "xguo", "jcq"});

    boolean ret = abstractAccessSensorService.saveAllCurrentDataFromSensor();
    logger.debug("testSaveAllCurrentDataFromSensor:  return --> " + ret);
  }
}
