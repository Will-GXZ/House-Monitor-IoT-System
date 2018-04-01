package com.twl.xg.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twl.xg.config.AppConfig;
import com.twl.xg.config.HibernateConfig;
import com.twl.xg.config.ServletInitializer;
import com.twl.xg.dao.SensorDataRepository;
import com.twl.xg.domain.*;
import com.twl.xg.service.DataFetchingAndMappingService;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static junit.framework.TestCase.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServletInitializer.class, AppConfig.class, HibernateConfig.class})
@WebAppConfiguration
public class TestDataFetchingAndMappingService {
  @Autowired
  DataFetchingAndMappingService dataFetchingAndMappingService;

  @Autowired
  SessionFactory sessionFactory;

  @Autowired
  SensorDataRepository sensorDataRepository;

  private static final Logger logger = Logger.getLogger(TestDataFetchingAndMappingService.class);


  @Test
  @Transactional
  public void testGetDataForSensorLaterThanFromDB() {
    SensorEntity sensor = (SensorEntity)sessionFactory
        .getCurrentSession()
        .createQuery("FROM SensorEntity ORDER BY rand()")
        .setMaxResults(1)
        .uniqueResult();
    SensorWrapper sensorWrapper = dataFetchingAndMappingService.getDataForSensorFromDB(sensor.getSensorIp(), new Date(1522374665833l));
    // test non exist sensor ip
    SensorWrapper sensorWrapper1 = dataFetchingAndMappingService.getDataForSensorFromDB("nonexistIP", new Date(1522374665833l));
    if (sensorWrapper1 != null) {
      fail("************* testGetDataForSensorLaterThanFromDB failed *************");
    }
  }

  @Test
  @Transactional
  public void testGetDataForBorderRouterFromDB() throws JsonProcessingException {
    BorderRouterEntity borderRouterEntity = (BorderRouterEntity) sessionFactory
                                            .getCurrentSession()
                                            .createQuery("FROM BorderRouterEntity ORDER BY rand()")
                                            .setMaxResults(1)
                                            .uniqueResult();
    BorderRouterWrapper borderRouterWrapper = dataFetchingAndMappingService
                                             .getDataForBorderRouterFromDB(borderRouterEntity.getBorderRouterIp(), null);
    ObjectMapper mapper = new ObjectMapper();
    logger.debug(mapper.writeValueAsString(borderRouterWrapper));
    // test for non-exist border router ip
    dataFetchingAndMappingService.getDataForBorderRouterFromDB("nonExistIP", null);

//    // test if a border router doesn't have sensors connected
//    sessionFactory.getCurrentSession()
//        .createQuery("DELETE FROM SensorEntity WHERE borderRouterByBorderRouterIp.borderRouterIp = '" + borderRouterEntity.getBorderRouterIp() + "'")
//        .executeUpdate();
//    dataFetchingAndMappingService.getDataForBorderRouterFromDB(borderRouterEntity.getBorderRouterIp(), null);

    // test if a border router has no data generated
    sessionFactory.getCurrentSession()
        .createQuery("DELETE FROM SensorDataEntity data WHERE data.sensorBySensorIp IN (FROM SensorEntity WHERE borderRouterByBorderRouterIp.borderRouterIp = :borderRouterIp)")
        .setParameter("borderRouterIp", borderRouterEntity.getBorderRouterIp())
        .executeUpdate();
    dataFetchingAndMappingService.getDataForBorderRouterFromDB(borderRouterEntity.getBorderRouterIp(), null);
  }

  @Test
  @Transactional
  public void testGetAllDataFromDB() throws JsonProcessingException {
    // should not have any data after now
    DataPackage dataPackage = dataFetchingAndMappingService.getAllDataFromDB(new Date());
    if (dataPackage != null) {
      fail("************** testGetAllDataFromDB failed ***************");
    }
    // test for all data
    dataPackage = dataFetchingAndMappingService.getAllDataFromDB(null);
    ObjectMapper mapper = new ObjectMapper();
    logger.debug(mapper.writeValueAsString(dataPackage));
    if (dataPackage.getSize() != sensorDataRepository.size()) {
      fail("************** testGetAllDataFromDB failed ***************");
    }
  }
}
