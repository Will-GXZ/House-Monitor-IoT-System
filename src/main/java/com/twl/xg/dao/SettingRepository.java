package com.twl.xg.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Repository
public class SettingRepository {
  @Autowired
  private SessionFactory sessionFactory;

  private static final Logger logger = Logger.getLogger(SettingRepository.class);

  // TODO
}
