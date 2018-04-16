package com.twl.xg.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableWebMvc
@Configuration
@EnableTransactionManagement
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@PropertySource({"classpath:app_custom.properties"})
@ComponentScan(basePackages = {"com.twl.xg.controller", "com.twl.xg.service",
                               "com.twl.xg.dao", "com.twl.xg.domain", "com.twl.xg.service.mock_impl",
                               "com.twl.xg.service.impl"})
public class AppConfig implements WebMvcConfigurer {
  private static final Logger logger = Logger.getLogger(AppConfig.class);
  @Autowired
  private Environment env;

  /**
   * Create an internalResourceViewResolver bean. This ViewResolver is responsible
   * for locating the jsp files in <code>WEB-INF/jsp</code> folder.
   *
   * @return ViewResolver
   */
  @Bean
  public ViewResolver jspViewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setViewClass(JstlView.class);
    resolver.setPrefix("/WEB-INF/jsp/");
    resolver.setSuffix(".jsp");
    return resolver;
  }

  /**
   * Configure simple automated controllers pre-configured with the response
   * status code and/or a view to render the response body. This is useful in
   * cases where there is no need for custom controller logic -- e.g. render a
   * home page, perform simple site URL redirects, return a 404 status with
   * HTML content, a 204 with no content, and more.
   *
   * In this implementation, this method redirects the root url <code>":"</code>
   * to the index page which is the <code>index.jsp</code> file in
   * <code>WEB-INF/jsp</code> folder.
   *
   */
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("redirect:index");
    registry.addViewController("/index").setViewName("index");
  }

  /**
   * Configure the ResourceHandler, enable access to static <code>html, js, css.etc</code>
   * files in <code></>/resources/</code> folder.
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**").addResourceLocations("/resources/");
    registry.addResourceHandler("/js/**").addResourceLocations("/resources/theme1/js/");
    registry.addResourceHandler("/css/**").addResourceLocations("/resources/theme1/css/");
    registry.addResourceHandler("/imgs/**").addResourceLocations("/resources/theme1/imgs/");
  }

  /**
   * Register spring bean of dataTypeList, this is the data types that we need to fetch from
   * each sensor. For example, <code>[temperature, humidity, lightness]</code>. The default
   * value of the list is read from <code>app_custom.properties</code> file.
   *
   * @return List of data types
   */
  @Bean(value = "dataTypeList")
  public List<String> addBeanDataTypeList() {
    String dataTypeListStr = env.getProperty("app.bean.dataTypeList");
    logger.debug("addBeanDataTypeList:  Read data type list property --> " + dataTypeListStr);
    return new ArrayList<String>(Arrays.asList(dataTypeListStr.split(",")));
  }
}
