package com.twl.xg.controller;

import com.twl.xg.service.DataFetchingAndMappingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    logger.debug("Accept request to set data types to: " + Arrays.toString(dataTypes));
    return dataFetchingAndMappingService.SetDataTypes(dataTypes);
  }
}
