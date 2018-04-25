package com.twl.xg.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * Configure Hibernate framework and spring transaction manager in this class.
 *
 * @author Xiaozheng Guo
 * @version 1.0
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Configuration
@PropertySource({"classpath:persistence-mysql.properties"})
public class HibernateConfig {
  @Autowired
  private Environment env;

  /**
   * Define <code>dataSource</code> bean. The DataSource object is needed by
   * SessionFactoryBean.
   *
   * @return dataSource
   */
  @Bean
  public DataSource dataSource() {
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
    dataSource.setUrl(env.getProperty("jdbc.url"));
    dataSource.setUsername(env.getProperty("jdbc.user"));
    dataSource.setPassword(env.getProperty("jdbc.pass"));

    return dataSource;
  }

  /**
   * Define <code>Properties</code> bean, which is needed to set up hibernate
   * <code>SessionFactoryBean</code>.
   *
   * @return properties
   */
  @Bean
  Properties hibernateProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
    properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
    properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
    properties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));

    return properties;
  }

  /**
   * Define <code>LocalSessionFactoryBean</code>, it bootstraps the SessionFactory
   * from annotation scanning. And it can also provide a <code>SessionFactory</code>
   * which is needed to get the <code>Session</code> object.
   *
   * @return sessionFactoryBean
   */
  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan("com.twl.xg.domain");
    sessionFactory.setHibernateProperties(hibernateProperties());

    return sessionFactory;
  }

  /**
   * Configure the <code>HibernateTransactionManager</code>, binds a Hibernate
   * Session from the specified factory to the thread.  SessionFactory.getCurrentSession()
   * is required for Hibernate access code that needs to support this transaction
   * handling mechanism, with the SessionFactory being configured with SpringSessionContext.
   *
   * @param sessionFactory An autowired SessionFactory object
   * @return hibernateTransactionManager
   */
  @Bean
  @Autowired
  public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
    HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
    hibernateTransactionManager.setSessionFactory(sessionFactory);

    return hibernateTransactionManager;
  }

}
