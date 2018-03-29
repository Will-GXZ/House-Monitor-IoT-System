package com.twl.xg.test.dao;

import com.twl.xg.config.AppConfig;
import com.twl.xg.config.HibernateConfig;
import com.twl.xg.config.ServletInitializer;
import com.twl.xg.dao.SensorDataRepository;
import com.twl.xg.domain.SensorDataEntity;
import com.twl.xg.domain.SensorEntity;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static junit.framework.TestCase.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServletInitializer.class, AppConfig.class, HibernateConfig.class})
@WebAppConfiguration
public class TestSensorDataRepository {
  @Autowired
  SessionFactory sessionFactory;

  @Autowired
  SensorDataRepository sensorDataRepository;

  private static final Logger logger = Logger.getLogger(TestSensorDataRepository.class);

  @Test
  @Transactional
  public void testSave() {
    // get a random sensor entity first
    String hql = "FROM SensorEntity ORDER BY rand()";
    SensorEntity sensorEntity = (SensorEntity)sessionFactory
                                .getCurrentSession()
                                .createQuery(hql).setMaxResults(1).uniqueResult();
    SensorDataEntity newSensor = new SensorDataEntity();
    newSensor.setSensorBySensorIp(sensorEntity);
    newSensor.setDataJson("testDataString");
    logger.debug("testSave: random sensor ip = " + sensorEntity.getSensorIp());
    String id = sensorDataRepository.save(newSensor);

    // verify if the saving operation is successful
//    hql = "FROM SensorDataEntity d WHERE d.sensorIp = 'new_sensor, ip = " + sensorEntity.getSensorIp()+ "'";
    hql = "FROM SensorDataEntity d WHERE d.dataJson = 'testDataString'";
    SensorDataEntity savedData = (SensorDataEntity)sessionFactory
                                  .getCurrentSession()
                                  .createQuery(hql)
                                  .uniqueResult();
    if (savedData != null) {
      logger.debug("saved data senser ip = " + savedData.getSensorIp());
      logger.debug("*********** testSave passed ***********");
    } else {
      fail("************ testSave failed *************");
    }
  }

  @Test
  @Transactional
  public void testGetAll() {
    List<SensorDataEntity> results = sensorDataRepository.getAll();
    int count = ((Long)sessionFactory
                .getCurrentSession()
                .createQuery("SELECT count(*) FROM SensorDataEntity")
                .uniqueResult())
                .intValue();
    if (results.size() == count) {
      int lastid = -1;
      for (SensorDataEntity sensorDataEntity : results) {
        if (sensorDataEntity.getId() < lastid) {
          fail("********* testGetAll failed, id is not sorted **********");
          lastid = sensorDataEntity.getId();
        }
      }
      logger.debug("********** testGetAll passed ************");
    } else {
      fail("********* testGetAll failed, result list size is not equal to table size **********");
    }
  }

  @Test
  @Transactional
  public void testGetAllBySensorId() {
    // get a random sensor first
    SensorEntity sensor = (SensorEntity)sessionFactory
                          .getCurrentSession()
                          .createQuery("FROM SensorEntity ORDER BY rand()")
                          .setMaxResults(1)
                          .uniqueResult();

    List<SensorDataEntity> results = sensorDataRepository.getAll(sensor.getSensorIp());
    int count = ((Long)sessionFactory
        .getCurrentSession()
        .createQuery("SELECT count(*) FROM SensorDataEntity s WHERE s.sensorIp = '" + sensor.getSensorIp() + "'")
        .uniqueResult()).intValue();
    if (count != results.size()) {
      fail("********** testGetAllSensorId failed: results size not equal to the number of data entries of the input sensor ip ************");
    }
    for (SensorDataEntity sensorDataEntity : results) {
      if (! sensorDataEntity.getSensorIp().equals(sensor.getSensorIp())) {
        fail("********** testGetAllSensorId failed: sensor IP not match ***********");
      }
    }
    logger.debug("************** testGetAllSensorId passed ***************");
  }

  @Test
  @Transactional
  public void testSize() {
    sensorDataRepository.size();
  }

  @Test
  @Transactional
  public void testClear() {
    sensorDataRepository.clear();
    long count = sensorDataRepository.size();
    if (count != 0) {
      fail("************ testClear failed *************");
    } else {
      logger.debug("*********** testClear passed ************");
    }
  }
}
