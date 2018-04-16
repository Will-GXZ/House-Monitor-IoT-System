package com.twl.xg.domain;

import java.util.ArrayList;
import java.util.List;

public class DataPackage {
  private int size;
  private List<BorderRouterWrapper> borderRouterWrapperList;

  public DataPackage(List<BorderRouterWrapper> borderRouterWrapperList) {
    if (borderRouterWrapperList == null) {
      size = 0;
      return;
    }
    this.borderRouterWrapperList = new ArrayList<>(borderRouterWrapperList);
    for (BorderRouterWrapper borderRouterWrapper : borderRouterWrapperList) {
      size += borderRouterWrapper.getSize();
    }
  }

  public int getSize() {
    return size;
  }

  public List<BorderRouterWrapper> getBorderRouterWrapperList() {
    return borderRouterWrapperList;
  }
}
