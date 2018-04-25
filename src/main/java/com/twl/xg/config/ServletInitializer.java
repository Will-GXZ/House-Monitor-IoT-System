package com.twl.xg.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * This is a <code>ServletInitializer</code> class, Servlet3.0+ container (tomcat here) will
 * pick up this class and run it automatically. This is the replacement for <code>web.xml</code>.
 *
 * @author Xiaozheng Guo
 * @version 1.0
 */
public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  /**
   * Specify {@code @Configuration} and/or {@code @Component} classes for the
   * {@linkplain #createRootApplicationContext() root application context}.
   *
   * @return the configuration for the root application context, or {@code null}
   * if creation and registration of a root context is not desired
   */
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class[] {AppConfig.class, HibernateConfig.class, TaskSchedulerConfig.class};
  }

  /**
   * Specify {@code @Configuration} and/or {@code @Component} classes for the
   * {@linkplain #createServletApplicationContext() Servlet application context}.
   *
   * @return the configuration for the Servlet application context, or
   * {@code null} if all configuration is specified through root config classes.
   *
   * Since we only have one dispatcher servlet, we can have only one root context.
   */
  @Override
  protected Class<?>[] getServletConfigClasses() {
    return null;
  }

  /**
   * Specify the servlet mapping(s) for the {@code DispatcherServlet} &mdash;
   * for example {@code "/"}, {@code "/app"}, etc.
   */
  @Override
  protected String[] getServletMappings() {
    return new String[]{"/"};
  }
}
