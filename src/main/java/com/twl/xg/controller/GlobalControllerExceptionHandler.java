package com.twl.xg.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Configure the global default exception handler.
 *
 * @author Xiaozheng Guo
 * @version 1.0
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
  private static final String DEFAULT_ERROR_VIEW = "error_page";
  private static final Logger logger = Logger.getLogger(GlobalControllerExceptionHandler.class);
  /**
   * Handle exceptions threw by controllers.
   *
   * @param req HttpServletRequest instance
   * @param e Exception instance
   * @return Default error handler view
   */
  @ExceptionHandler(value = Exception.class)
  public ModelAndView defaultErrorHandler(HttpServletRequest req, HttpServletResponse resp, Exception e) {
    ModelAndView mav = new ModelAndView();
    resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    mav.addObject("exception", e);
    logger.error("defaultErrorHandler: --> " + e.getMessage());
    mav.addObject("url", req.getRequestURL());
    mav.setViewName(DEFAULT_ERROR_VIEW);
    return mav;
  }
}
