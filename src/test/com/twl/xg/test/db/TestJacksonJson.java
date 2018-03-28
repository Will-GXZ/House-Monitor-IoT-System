package com.twl.xg.test.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twl.xg.config.AppConfig;
import com.twl.xg.config.HibernateConfig;
import com.twl.xg.config.ServletInitializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServletInitializer.class, AppConfig.class, HibernateConfig.class})
@WebAppConfiguration
public class TestJacksonJson {
  @Test
  public void test1() throws IOException {
    Map<String, Double> map = new HashMap<>();
    map.put("temperature", 20.55);
    map.put("humidity", 10.3);
    map.put("lightness", 72.44);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(System.out, map);
  }
}
