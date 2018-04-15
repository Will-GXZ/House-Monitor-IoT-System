package com.twl.xg.controller;

import com.twl.xg.service.DataFetchingAndMappingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

  @Autowired
  private DataFetchingAndMappingService dataFetchingAndMappingService;

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
  public String dataTypeSettingPage(Model model) {
    logger.debug("dataTypeSettingPage: request accepted");
    model.addAttribute("dataTypeList", context.getBean("dataTypeList"));
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
      logger.error("setBorderRouterPage: dataTypeList has not been initialized, redirect.");
      return "redirect:/page/dataTypeSettingPage";
    }
    return "set_border_router";
  }

  /**
   * Send set_sensor_name view to browser. If <code>dataTypeList</code> has not been
   * initialized, redirect to <code>dataTypeSettingPage</code>. Else if there is no
   * border router in database, redirect to <code>setBorderRouterPage</code>.
   *
   * @return set sensor name view.
   */
  @RequestMapping("/setSensorNamePage")
  public String setSensorNamePage() {
    logger.debug("setSensorName: request accepted.");
    if (((List) context.getBean("dataTypeList")).isEmpty()) {
      logger.error("setSensorNamePage: dataTypeList has not been initialized, redirect.");
      return "redirect:/page/dataTypeSettingPage";
    } else if (dataFetchingAndMappingService.getBorderRouterNumber() == 0) {
      logger.error("setSensorNamePage: no border router in database, redirect");
      return "redirect:/page/setBorderRouterPage";
    } else {
      return "set_sensor_name";
    }
  }

  /**
   * Send monitor_data view to browser. If <code>dataTypeList</code> has not been
   * initialized, redirect to <code>dataTypeSettingPage</code>. Else if there is no
   * border router in database, redirect to <code>setBorderRouterPage</code>. Sensor
   * name doesn't actually matter, because if the user has not set sensor name, the
   * default name will be the sensor's IP.
   *
   * @return monitor data view name.
   */
  @RequestMapping("/monitorDataPage")
  public String monitorDataPage() {
    logger.debug("monitorDataPage: request accepted.");
    if (((List) context.getBean("dataTypeList")).isEmpty()) {
      logger.error("monitorDataPage: dataTypeList has not been initialized, redirect.");
      return "redirect:/page/dataTypeSettingPage";
    } else if (dataFetchingAndMappingService.getBorderRouterNumber() == 0) {
      logger.error("monitorDataPage: no border router in database, redirect");
      return "redirect:/page/setBorderRouterPage";
    } else {
      return "monitor_data";
    }
  }
}
