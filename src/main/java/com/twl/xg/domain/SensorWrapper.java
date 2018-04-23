package com.twl.xg.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Use this class as a JSON model, an instance of <code>SensorWrapper</code>
 * contains an <code>SensorEntity</code> and a list of <code>SensorDataEntity</code>.
 *
 * @see SensorEntity
 * @author Xiaozheng Guo
 * @version 1.0
 */
public class SensorWrapper {
  private SensorEntity sensor;
  private List<SensorDataEntity> dataList;

  public SensorWrapper(SensorEntity sensor) {
    this.sensor = sensor;
  }

  public void setDataList(List<SensorDataEntity> dataList) {
    this.dataList = new ArrayList<>(dataList);
  }

  public List<SensorDataEntity> getDataList() {
    return dataList;
  }

  public SensorEntity getSensor() {
    return sensor;
  }
}
