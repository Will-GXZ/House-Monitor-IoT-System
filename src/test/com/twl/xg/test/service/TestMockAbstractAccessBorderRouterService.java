package com.twl.xg.test.service;

import com.twl.xg.config.AppConfig;
import com.twl.xg.config.HibernateConfig;
import com.twl.xg.config.ServletInitializer;
import com.twl.xg.config.TaskSchedulerConfig;
import com.twl.xg.dao.BorderRouterRepository;
import com.twl.xg.domain.BorderRouterEntity;
import com.twl.xg.service.AbstractAccessBorderRouterService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static junit.framework.TestCase.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServletInitializer.class, AppConfig.class, HibernateConfig.class, TaskSchedulerConfig.class})
@WebAppConfiguration
public class TestMockAbstractAccessBorderRouterService {
  @Autowired
  @Qualifier(value = "mockAccessBorderRouterService")
  AbstractAccessBorderRouterService abstractAccessBorderRouterService;
  @Autowired
  BorderRouterRepository borderRouterRepository;

  private static final Logger logger = Logger.getLogger(TestMockAbstractAccessBorderRouterService.class);

  @Test
  @Transactional
  public void testSaveBorderRouter() {
    String borderRouterIp = "testIP", borderRouterName = "testName";
    BorderRouterEntity savedBorderRouterEntity = abstractAccessBorderRouterService.saveBorderRouter(borderRouterIp, borderRouterName);
    if (savedBorderRouterEntity != null && savedBorderRouterEntity.equals(borderRouterRepository.get(borderRouterIp))) {
      logger.debug("************** testSaveBorderRouter passed **************");
    } else {
      fail("*************** testSaveBorderRouter failed ***************");
    }
  }

  @Test
  public void testGetSensorIpByBorderRouterIp() {
    String testBorderRouterIp = "testIP";
    abstractAccessBorderRouterService.getSensorIpByBorderRouterIp(testBorderRouterIp);
  }

  @Test
  @Transactional
  public void testSaveSensorsForBorderRouterIp() {
    String borderRouterIp = "testBorderRouterIP";
    List<String> sensorIpList = abstractAccessBorderRouterService.getSensorIpByBorderRouterIp(borderRouterIp);
    abstractAccessBorderRouterService.saveBorderRouter(borderRouterIp, "testBorderRouterName");
    abstractAccessBorderRouterService.saveSensorsForBorderRouterIp(borderRouterIp);
  }
}
