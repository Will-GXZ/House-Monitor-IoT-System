package com.twl.xg.service;

import com.twl.xg.dao.TestUserDao;
import com.twl.xg.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class is only for testing.
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class TestService {
  @Autowired
  private TestUserDao userDao;

  public void save(UserEntity user) {
    userDao.save(user);
  }
}
