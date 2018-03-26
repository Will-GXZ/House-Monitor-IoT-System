package com.twl.xg.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Repository
public class BorderRouterRepository {
  @Autowired
  private SessionFactory sessionFactory;

  // TODO
}
