package com.twl.xg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"com.twl.xg.controller", "com.twl.xg.service"})
public class AppConfig implements WebMvcConfigurer {

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
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
  }
}
