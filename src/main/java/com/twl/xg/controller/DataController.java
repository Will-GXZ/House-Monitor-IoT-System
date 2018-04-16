package com.twl.xg.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twl.xg.domain.BorderRouterEntity;
import com.twl.xg.domain.BorderRouterWrapper;
import com.twl.xg.domain.DataPackage;
import com.twl.xg.service.AbstractAccessBorderRouterService;
import com.twl.xg.service.AbstractAccessSensorService;
import com.twl.xg.service.DataFetchingAndMappingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
  @Qualifier("mockAccessBorderRouterService")
  AbstractAccessBorderRouterService accessBorderRouterService;
  @Autowired
  @Qualifier("mockAccessSensorService")
  AbstractAccessSensorService accessSensorService;
  @Autowired
  ApplicationContext context;

  private static final Logger logger = Logger.getLogger(DataController.class);


  /**
   * Gets all current data from each sensor, not from database. Also these data
   * entries will not be stored in database.
   *
   * @return An instance of DataPackage that contains all current data.
   */
  @RequestMapping(value = "/get/all/current",
                  method = RequestMethod.GET,
                  headers = "ModelAttribute=getAllDataFromSensor",
                  produces = "application/json",
                  consumes = "application/json")
  public @ResponseBody DataPackage getAllCurrentData() throws JsonProcessingException {
    logger.debug("getAllCurrentData:  Request accepted");
    DataPackage dataPackage = accessSensorService.getAllCurrentSensorData();
    if (dataPackage == null) {
      return new DataPackage(null);
    } else {
      return dataPackage;
    }
  }

  /**
   * Gets all data from database, map to a <code>DataPackage</code> object.
   *
   * @return An instance of DataPackage that contains all data in database.
   */
  @RequestMapping(value = "/get/all/database",
                  method = RequestMethod.GET,
                  headers = "ModelAttribute=getAllDataFromDatabase",
                  produces = "application/json",
                  consumes = "application/json")
  public @ResponseBody DataPackage getAllDataFromDatabase() {
    logger.debug("getAllDataFromDataBase:  Request accepted");
    DataPackage dataPackage = dataFetchingAndMappingService.getAllDataFromDB(null);
    if (dataPackage == null) {
      return new DataPackage(null);
    } else {
      return dataPackage;
    }
  }

  /**
   * Fetching data from database for given border router.
   *
   * @return border router wrapper
   */
  @RequestMapping(value = "/get/{borderRouterIp}/database",
      method = RequestMethod.GET,
      headers = "ModelAttribute=getDataForBorderRouterFromDatabase",
      produces = "application/json",
      consumes = "application/json")
  public @ResponseBody BorderRouterWrapper getDataForBorderRouterFromDB(@PathVariable String borderRouterIp) {
    logger.debug("getDataForBorderRouterFromDB: Request accepted. Router IP --> " + borderRouterIp);
    return dataFetchingAndMappingService.getDataForBorderRouterFromDB(borderRouterIp, null);
  }

  /**
   * Delete all data in database. Return the number of entries deleted.
   *
   * @return the number of deleted entries.
   */
  @RequestMapping(value = "/delete/all",
                  method = RequestMethod.DELETE,
                  headers = "ModelAttribute=deleteAllData",
                  produces = "application/json",
                  consumes = "application/json")
  public @ResponseBody int clearAllData() {
    logger.debug("clearAllData:  Request accepted");
    return dataFetchingAndMappingService.clearSensorData();
  }

  /**
   * Fetching data from sensor for given border router. Return null if the input border
   * router IP doesn't exist in database.
   *
   * @param borderRouterIp parameter from path string
   * @return an instance of <code>BorderRouterWrapper</code>
   */
  @RequestMapping(value = "/get/{borderRouterIp}/sensor",
      method = RequestMethod.GET,
      headers = "ModelAttribute=getDataForBorderRouterFromSensor",
      produces = "application/json",
      consumes = "application/json")
  public @ResponseBody BorderRouterWrapper getCurrentDataForBorderRouter(@PathVariable String borderRouterIp) throws JsonProcessingException {
    logger.debug("getCurrentDataForBorderRouter: Request accepted. Border router IP --> " + borderRouterIp);
    BorderRouterEntity borderRouterEntity = dataFetchingAndMappingService.getBorderRouterEntity(borderRouterIp);
    if (borderRouterEntity == null) {
      return null;
    }
    return accessSensorService.getDataFromSensorForBorderRouter(borderRouterEntity);
  }

  /**
   * get a list of "borderRouterIP, borderRouterName" pair for all border router in database.
   * If there is no borderRouter in database, return an empty list.
   *
   * @return a list of "borderRouterIP, borderRouterName" pair
   */
  @RequestMapping(value = "/get/borderRouterIpAndName",
      method = RequestMethod.GET,
      headers = "ModelAttribute=getAllBorderRouterIpAndName",
      produces = "application/json",
      consumes = "application/json")
  public @ResponseBody List<String[]> getAllBorderRouterIpAndName() {
    logger.debug("getAllBorderRouterIpAndName: Request accepted.");
    return dataFetchingAndMappingService.getAllBorderRouterIpAndName();
  }
}
