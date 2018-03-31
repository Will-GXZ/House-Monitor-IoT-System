package com.twl.xg.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Use this class as the JSON model for borderRouter, an instance of <code>BorderRouterWrapper</code>
 * contains an instance of <code>BorderRouterEntity</code> and a list of <code>SensorWrapper</code>.
 */
public class BorderRouterWrapper {
  private BorderRouterEntity borderRouter;
  private List<SensorWrapper> sensorWrapperList;
  private int size;

  public BorderRouterWrapper(BorderRouterEntity borderRouter) {
    this.borderRouter = borderRouter;
  }

  public void setSensorWrapperList(List<SensorWrapper> sensorWrapperList) {
    this.sensorWrapperList = new ArrayList<>(sensorWrapperList);
    for (SensorWrapper sensorWrapper : sensorWrapperList) {
      size += sensorWrapper.getDataList().size();
    }
  }

  public int getSize() {
    return size;
  }

  public List<SensorWrapper> getSensorWrapperList() {
    return sensorWrapperList;
  }

  public BorderRouterEntity getBorderRouter() {
    return borderRouter;
  }
}
