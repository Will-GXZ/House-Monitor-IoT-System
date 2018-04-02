package com.twl.xg.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twl.xg.service.AbstractAccessBorderRouterService;
import com.twl.xg.service.DataFetchingAndMappingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;


/**
 * This is the setting controller class. This controller accept URL pattern:
 * <code>/setting/**</code>. All setting requests including customizing data
 * types are handled by this controller.
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
@RequestMapping("/setting")
public class SettingController {
  @Autowired
  DataFetchingAndMappingService dataFetchingAndMappingService;

  @Autowired
  @Qualifier(value = "mockAccessBorderRouterService")
  AbstractAccessBorderRouterService abstractAccessBorderRouterService;

  @Autowired
  ApplicationContext context;

  private static final Logger logger = Logger.getLogger(SettingController.class);

  /**
   * This handler accept request to set all data types the user want to query. Every
   * time the <code>dataTypeList</code> gets updated, the <code>sensor_data</code>
   * table in database will be cleared. Because the number of data fields have been
   * changed.
   * The response body contains JSON of the data type list. The input data type list
   * has to contains at least one data type.
   * @param dataTypes An array of data types string, this array is generated from JSON
   *                  request body.
   * @return Return a list of String which contains all data types name set by user.
   */
  @RequestMapping(value = "/dataTypes",
                  method = RequestMethod.POST,
                  headers = "ModelAttribute=DataTypes",
                  produces = "application/json",
                  consumes = "application/json")
  public @ResponseBody List<String> setDataTypes(@RequestBody String[] dataTypes) {
    logger.debug("SettingController:  Accept request to set data types to: " + Arrays.toString(dataTypes));
    return dataFetchingAndMappingService.setDataTypes(dataTypes);
  }

  /**
   * This handler sets up border router IP addresses for the server. For setting up
   * border router IP, we also need to add sensors that connected to each border
   * router into database. If a border router IP address doesn't exist, we just
   * ignore that border router IP.
   *
   * Before we add these border router into database, we need to clear the database
   * first. Because we are initialing the server.
   *
   * For this API, one need to send a JSON string which is a 2D array, each row
   * is a key-value pair of router IP and name.
   *
   * @param routerIpAndName The input array of border router IP and name, key-value pair
   * @return "HTTP_OK" indicates the operation is successful.
   */
  @RequestMapping(value = "/setBorderRouter",
                  method = RequestMethod.POST,
                  headers = "ModelAttribute=borderRouterIpAndName",
                  produces = "application/json",
                  consumes = "application/json")
  public @ResponseBody String saveBorderRouter(@RequestBody String[][] routerIpAndName) throws JsonProcessingException {
    logger.debug("saveBorderRouter:  Set border router IP and name --> " + new ObjectMapper().writeValueAsString(routerIpAndName));
    // clear database first
    dataFetchingAndMappingService.clearDataBase();
    // For each border router IP address, get sensors connected to the router, store them in database
    for (String[] pair : routerIpAndName) {
      if (abstractAccessBorderRouterService.existBorderRouter(pair[0])) {
        abstractAccessBorderRouterService.saveBorderRouter(pair[0], pair[1]);
        abstractAccessBorderRouterService.saveSensorsForBorderRouterIp(pair[0]);
      }
    }
    return "HTTP_OK";
  }

  /**
   * Set name for each sensor. Restful API.
   *
   * @param sensorIpAndName A 2D String array, each row is a sensorIP-sensorName pair.
   * @return "HTTP_OK" indicates the operation is successful.
   * @throws JsonProcessingException
   */
  @RequestMapping(value = "/setSensorName",
                  method = RequestMethod.PUT,
                  headers = "ModelAttribute=sensorIpAndName",
                  produces = "application/json",
                  consumes = "application/json")
  public @ResponseBody String setSensorName(@RequestBody String[][] sensorIpAndName) throws JsonProcessingException {
    logger.debug("setSensorName:  Set name for each sensor --> " + new ObjectMapper().writeValueAsString(sensorIpAndName));
    for (String[] pair : sensorIpAndName) {
      if (! dataFetchingAndMappingService.updateSensorName(pair[0], pair[1])) {
        logger.error("setSensorName:  Input sensorIp doesn't exist: ip = " + pair[0]);
      }
    }
    return "HTTP_OK";
  }


  /**
   * Gets all sensor ip. Restful API.
   *
   * @return A list of <code>String</code> contains all sensor IP addresses.
   */
  @RequestMapping(value = "/getAllSensorIp",
                  method = RequestMethod.GET,
                  headers = "ModelAttribute=getAllSensorIp",
                  produces = "application/json",
                  consumes = "application/json")
  public @ResponseBody List<String> getAllSensorIp() {
    logger.debug("getAllSensorIp:  GET request accepted, get all sensor IP addresses");
    return dataFetchingAndMappingService.getAllSensorIp();
  }
}
