package com.twl.xg.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * This controller is mainly responsible for provide views to browser.
 */

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
@RequestMapping("/page")
public class PageController {
  @Autowired
  private ApplicationContext context;

  private static final Logger logger = Logger.getLogger(PageController.class);

  /**
   * This is the general error page of the application.
   *
   * @return error page view
   */
  @RequestMapping("/errorPage")
  public String errorPage() {
    logger.debug("errorPage: request accepted");
    return "general_error_page";
  }

  /**
   * This is the first setting page
   *
   * @return The view name
   */
  @RequestMapping("/dataTypeSettingPage")
  public String dataTypeSettingPage() {
    logger.debug("dataTypeSettingPage: request accepted");
    return "set_data_type";
  }

  /**
   * Send border router setting view to browser. If <code>dataTypeList</code> has
   *  not been setup yet, redirect to <code>dataTypeSettingPage</code>.
   *
   * @return The view name
   */
  @RequestMapping("/setBorderRouterPage")
  public String setBorderRouterPage() {
    logger.debug("setBorderRouterPage: request accepted");
    // if dataTypeList is empty, redirect to dataTypeSettingPage
    if (((List) context.getBean("dataTypeList")).isEmpty()) {
      logger.debug("setBorderRouterPage: dataTypeList has not been initialized, redirect.");
      return "redirect:/page/dataTypeSettingPage";
    }
    return "set_border_router";
  }
}
