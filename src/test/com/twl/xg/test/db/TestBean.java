package com.twl.xg.test.db;

import com.twl.xg.config.AppConfig;
import com.twl.xg.config.HibernateConfig;
import com.twl.xg.config.ServletInitializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.Basic;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServletInitializer.class, AppConfig.class, HibernateConfig.class})
@WebAppConfiguration
public class TestBean {
  @Autowired
  ApplicationContext context;

//  @Bean(value = "dataMap")
//  public Map<String, Double> createDataMap() {
//    Map<String, Double> map = new HashMap<>();
//    map.put("temp", 0.22);
//    return map;
//  }

  @Test
  public void test1() {
    Map<String, Double> map = (Map<String, Double>)context.getBean("dataMap");
    System.out.println(map.get("temp"));
  }
}
