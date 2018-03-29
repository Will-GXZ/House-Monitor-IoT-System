package com.twl.xg.test.dao;

import com.twl.xg.config.AppConfig;
import com.twl.xg.config.HibernateConfig;
import com.twl.xg.config.ServletInitializer;
import com.twl.xg.dao.BorderRouterRepository;
import com.twl.xg.domain.BorderRouterEntity;
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
public class TestBorderRouterRepository {
  @Autowired
  BorderRouterRepository borderRouterRepository;

  @Autowired
  SessionFactory sessionFactory;

  private static final Logger logger = Logger.getLogger(TestBorderRouterRepository.class);

  @Test
  @Transactional
  public void testSave() {
    // create a border router entity
    BorderRouterEntity newBorderRouter = new BorderRouterEntity();
    newBorderRouter.setBorderRouterIp("newIp");
    newBorderRouter.setBorderRouterName("newBorderRouter");

    borderRouterRepository.save(newBorderRouter);
    BorderRouterEntity fetched = (BorderRouterEntity)sessionFactory
        .getCurrentSession()
        .createQuery("FROM BorderRouterEntity b WHERE b.borderRouterIp = '" + newBorderRouter.getBorderRouterIp() + "'")
        .uniqueResult();
    if (newBorderRouter.equals(fetched)) {
      logger.debug("*********** testSave passed ***********");
    } else {
      fail("********** testSave failed ***********");
    }
  }

  @Test
  @Transactional
  public void testGetAll() {
    List<BorderRouterEntity> results = borderRouterRepository.getAll();
    long count = (Long)sessionFactory.getCurrentSession()
        .createQuery("SELECT count(*) FROM BorderRouterEntity")
        .uniqueResult();
    if (count == results.size()) {
      logger.debug("*********** testGetAll passed ************");
    } else {
      fail("*********** testGetAll failed ***********");
    }
  }

  @Test
  @Transactional
  public void testSize() {
    long size = borderRouterRepository.size();
    long count = (Long)sessionFactory.getCurrentSession()
        .createQuery("SELECT count(*) FROM BorderRouterEntity")
        .uniqueResult();
    if (size == count) {
      logger.debug("*********** testSize passed ************");
    } else {
      fail("********** testSize failed ***********");
    }
  }

  @Test
  @Transactional
  public void testClear() {
    borderRouterRepository.clear();
    long sensorCount = (Long)sessionFactory.getCurrentSession()
        .createQuery("SELECT count(*) FROM SensorDataEntity")
        .uniqueResult();
    long routerCount = (Long)sessionFactory.getCurrentSession()
        .createQuery("SELECT count(*) FROM BorderRouterEntity ")
        .uniqueResult();
    long dataCount = (Long)sessionFactory.getCurrentSession()
        .createQuery("SELECT count(*) FROM SensorDataEntity ")
        .uniqueResult();

    if (sensorCount == 0 && routerCount == 0 && dataCount == 0) {
      logger.debug("********* testClear passed **********");
    } else {
      fail("********** testClear failed **********");
    }
  }
}
