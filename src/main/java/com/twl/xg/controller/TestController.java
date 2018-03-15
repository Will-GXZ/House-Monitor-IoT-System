package com.twl.xg.controller;

import com.twl.xg.domain.UserEntity;
import com.twl.xg.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;

/**
 * This class is for testing.
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
@RequestMapping("/test/")
public class TestController {
  @Autowired
  private TestService testService;

  @RequestMapping(value = "/save", method = RequestMethod.POST, headers = "ModelAttribute=UserEntity")
  @ResponseBody
  public UserEntity saveHandler(@ModelAttribute UserEntity user) {
    if (user.getCreateTime() == null) {
      user.setCreateTime(new Timestamp(System.currentTimeMillis()));
    }
    System.out.println(user);
    testService.save(user);
    return user;
  }
}
