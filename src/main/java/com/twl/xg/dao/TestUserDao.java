package com.twl.xg.dao;

import com.twl.xg.domain.UserEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is only for testing Hibernate
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Repository
public class TestUserDao {
  @Autowired
  private SessionFactory sessionFactory;

  @Transactional
  public void save(UserEntity user) {
    sessionFactory.getCurrentSession().save(user);
  }
}
