package com.twl.xg.test.service;

import com.twl.xg.config.AppConfig;
import com.twl.xg.config.HibernateConfig;
import com.twl.xg.config.ServletInitializer;
import com.twl.xg.dao.BorderRouterRepository;
import com.twl.xg.domain.BorderRouterEntity;
import com.twl.xg.service.AccessBorderRouterService;
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
@ContextConfiguration(classes = {ServletInitializer.class, AppConfig.class, HibernateConfig.class})
@WebAppConfiguration
public class TestMockAccessBorderRouterService {
  @Autowired
  @Qualifier(value = "mockAccessBorderRouterServiceImpl")
  AccessBorderRouterService accessBorderRouterService;
  @Autowired
  BorderRouterRepository borderRouterRepository;

  private static final Logger logger = Logger.getLogger(TestMockAccessBorderRouterService.class);

  @Test
  @Transactional
  public void testSaveBorderRouter() {
    String borderRouterIp = "testIP", borderRouterName = "testName";
    BorderRouterEntity savedBorderRouterEntity = accessBorderRouterService.saveBorderRouter(borderRouterIp, borderRouterName);
    if (savedBorderRouterEntity != null && savedBorderRouterEntity.equals(borderRouterRepository.get(borderRouterIp))) {
      logger.debug("************** testSaveBorderRouter passed **************");
    } else {
      fail("*************** testSaveBorderRouter failed ***************");
    }
  }

  @Test
  public void testGetSensorIpByBorderRouterIp() {
    String testBorderRouterIp = "testIP";
    accessBorderRouterService.getSensorIpByBorderRouterIp(testBorderRouterIp);
  }

  @Test
  @Transactional
  public void testSaveSensorsForBorderRouterIp() {
    String borderRouterIp = "testBorderRouterIP";
    List<String> sensorIpList = accessBorderRouterService.getSensorIpByBorderRouterIp(borderRouterIp);
    accessBorderRouterService.saveBorderRouter(borderRouterIp, "testBorderRouterName");
    accessBorderRouterService.saveSensorsForBorderRouterIp(borderRouterIp);
  }
}
