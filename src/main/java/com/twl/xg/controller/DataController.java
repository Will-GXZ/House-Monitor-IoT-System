package com.twl.xg.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twl.xg.domain.DataPackage;
import com.twl.xg.service.AccessBorderRouterService;
import com.twl.xg.service.AccessSensorService;
import com.twl.xg.service.DataFetchingAndMappingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This is the data controller class. This Controller handles all requests that are
 * fetching sensor data from back-end. This is a restful controller, consume JSON,
 * produce JSON.
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
@RequestMapping("/data")
public class DataController {
  @Autowired
  DataFetchingAndMappingService dataFetchingAndMappingService;
  @Autowired
  @Qualifier("mockAccessBorderRouterServiceImpl")
  AccessBorderRouterService accessBorderRouterService;
  @Autowired
  @Qualifier("mockAccessSensorServiceImpl")
  AccessSensorService accessSensorService;

  private static final Logger logger = Logger.getLogger(DataController.class);


  /**
   * Gets all current data from each sensor, not from database. Also these data
   * entries will not be stored in database.
   *
   * @return An instance of DataPackage that contains all current data.
   */
  @RequestMapping(value = "/get/all",
                  method = RequestMethod.GET,
                  headers = "ModelAttribute=getAllData",
                  produces = "application/json",
                  consumes = "application/json")
  public @ResponseBody DataPackage getAllCurrentData() throws JsonProcessingException {
    logger.debug("getAllCurrentData:  Request accepted");
    DataPackage dataPackage = accessSensorService.getAllCurrentSensorData();
    return dataPackage;
  }

}
