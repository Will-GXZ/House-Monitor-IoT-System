package com.twl.xg.test.dao;

import com.twl.xg.config.AppConfig;
import com.twl.xg.config.HibernateConfig;
import com.twl.xg.config.ServletInitializer;
import com.twl.xg.dao.SensorRepository;
import com.twl.xg.domain.BorderRouterEntity;
import com.twl.xg.domain.SensorEntity;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static junit.framework.TestCase.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServletInitializer.class, AppConfig.class, HibernateConfig.class})
@WebAppConfiguration
public class TestSensorRepository {
  @Autowired
  SensorRepository sensorRepository;

  @Autowired
  SessionFactory sessionFactory;

  private static final Logger logger = Logger.getLogger(TestSensorRepository.class);

  @Test
  @Transactional
//  @Commit
  public void testUpdateSensorName() {
    SensorEntity sensorEntity = (SensorEntity) sessionFactory.getCurrentSession()
                                 .createQuery("FROM SensorEntity ORDER BY rand()")
                                 .setMaxResults(1)
                                 .uniqueResult();
    boolean ret = sensorRepository.updateSensorName(sensorEntity.getSensorIp(), "updatedName");
    if (ret) {
      logger.debug("************** testUpdateSensorName passed *****************");
    } else {
      fail("**************** testUpdateSensorName failed *****************");
    }
  }

  @Test
  @Transactional
  public void testGet() {
    // get a null
    SensorEntity res1 = sensorRepository.get("dummyIP");
    // get a existed sensor
    SensorEntity res2 = sensorRepository.get("borderRouterIp-1_sensor2");
  }

  @Test
  @Transactional
//  @Commit
  public void testClear() {
    sensorRepository.clear();
    Integer count = ((Long)sessionFactory.getCurrentSession().createQuery("SELECT COUNT(*) FROM SensorEntity").uniqueResult()).intValue();
    if (count == 0) {
      logger.debug("********* testClear passed **********");
    } else {
      fail("******** testClear failed **********");
    }
  }

  @Test
  @Transactional
  public void testSave() {
    // get a random border router from db first
    Query query = sessionFactory.getCurrentSession().createQuery("FROM BorderRouterEntity ORDER BY rand()").setMaxResults(1);
    BorderRouterEntity borderRouterEntity = (BorderRouterEntity)query.uniqueResult();

    SensorEntity sensorEntity = new SensorEntity();
    sensorEntity.setBorderRouterByBorderRouterIp(borderRouterEntity);
    sensorEntity.setSensorIp(borderRouterEntity.getBorderRouterIp() + "_new_added");
    sensorEntity.setSensorName("new_added_sensor");

    String id = sensorRepository.save(sensorEntity);

    if (sessionFactory.getCurrentSession().createQuery("SELECT 1 FROM SensorEntity s WHERE s.sensorIp = '" +
        borderRouterEntity.getBorderRouterIp() + "_new_added'").uniqueResult() != null) {
      logger.debug("********* testSave passed **********");
    } else {
      fail("*********** testSave failed *************");
    }

    // should throw NPE
    try {
      sensorRepository.save(null);
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
  }

  @Test
  @Transactional
  public void testGetAllSensor() {
    List<SensorEntity> results = sensorRepository.getAll();
    for (SensorEntity sensorEntity : results) {
      logger.debug("testGetAllSensor: get sensor name:  " + sensorEntity.getSensorName());
    }
    int totalNumber = ((Long)sessionFactory.getCurrentSession().createQuery("SELECT count(*) FROM SensorEntity").uniqueResult()).intValue();
    if (results.size() != totalNumber) {
      fail("********** testGetAllSensor failed ***********");
    } else {
      logger.debug("************ testGetAllSensor passed *************");
    }
  }

  @Test
  @Transactional
  public void testGetAllSensorForBorderRouterIp() {
    String borderRouterIp =
        ((BorderRouterEntity)sessionFactory
        .getCurrentSession()
        .createQuery("FROM BorderRouterEntity ORDER BY rand()")
        .setMaxResults(1).uniqueResult()).getBorderRouterIp();
    List<SensorEntity> results = sensorRepository.getAll(borderRouterIp);
    logger.debug("testGetAllSensorForBorderRouterIp:  get sensor for border router ip = " + borderRouterIp);
    for (SensorEntity sensorEntity : results) {
      logger.debug("testGetAllSensorForBorderRouterIp:  get sensor name= " + sensorEntity.getSensorName() + ", ip= " + sensorEntity.getSensorIp());
    }
    int totalNumber = ((Long)sessionFactory.getCurrentSession().createQuery("SELECT count(*) FROM SensorEntity s WHERE s.borderRouterByBorderRouterIp.borderRouterIp = '" + borderRouterIp + "'").uniqueResult()).intValue();
    if (results.size() != totalNumber) {
      fail("**************** testGetAllSensorForBorderRouterIp failed ****************");
    } else {
      logger.debug("*************** testGetAllSensorForBorderRouterIp passed ***************");
    }
  }

  @Test
  @Transactional
  public void testSize() {
    long size = sensorRepository.size();
  }

  @Transactional
  @Test
  public void testGetAllSensorIp() {
    List<String> res = sensorRepository.getAllSensorIp();
    for(String sensorIp : res) logger.debug("Sensor IP: " + sensorIp);
  }
}
