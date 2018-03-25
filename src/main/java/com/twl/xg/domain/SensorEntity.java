package com.twl.xg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@JsonIgnoreProperties(value = {"borderRouterByBorderRouterIp"})
@Entity
@Table(name = "sensor", schema = "testdb2")
public class SensorEntity {
  private String sensorIp;
  private String sensorName;
  private BorderRouterEntity borderRouterByBorderRouterIp;

  @Id
  @Column(name = "sensorIp", nullable = false, length = 30)
  public String getSensorIp() {
    return sensorIp;
  }

  public void setSensorIp(String sensorIp) {
    this.sensorIp = sensorIp;
  }

  @Basic
  @Column(name = "sensorName", nullable = false, length = 45)
  public String getSensorName() {
    return sensorName;
  }

  public void setSensorName(String sensorName) {
    this.sensorName = sensorName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SensorEntity that = (SensorEntity) o;

    if (sensorIp != null ? !sensorIp.equals(that.sensorIp) : that.sensorIp != null) return false;
    if (sensorName != null ? !sensorName.equals(that.sensorName) : that.sensorName != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = sensorIp != null ? sensorIp.hashCode() : 0;
    result = 31 * result + (sensorName != null ? sensorName.hashCode() : 0);
    return result;
  }

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "borderRouterIp", referencedColumnName = "borderRouterIp", nullable = false)
  public BorderRouterEntity getBorderRouterByBorderRouterIp() {
    return borderRouterByBorderRouterIp;
  }

  public void setBorderRouterByBorderRouterIp(BorderRouterEntity borderRouterByBorderRouterIp) {
    this.borderRouterByBorderRouterIp = borderRouterByBorderRouterIp;
  }
}
