package com.twl.xg.domain;

import javax.persistence.*;

@Entity
@Table(name = "border_router", schema = "testdb2")
public class BorderRouterEntity {
  private String borderRouterIp;
  private String borderRouterName;

  @Id
  @Column(name = "border_router_ip", nullable = false, length = 30)
  public String getBorderRouterIp() {
    return borderRouterIp;
  }

  public void setBorderRouterIp(String borderRouterIp) {
    this.borderRouterIp = borderRouterIp;
  }

  @Basic
  @Column(name = "border_router_name", nullable = false, length = 45)
  public String getBorderRouterName() {
    return borderRouterName;
  }

  public void setBorderRouterName(String borderRouterName) {
    this.borderRouterName = borderRouterName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BorderRouterEntity that = (BorderRouterEntity) o;

    if (borderRouterIp != null ? !borderRouterIp.equals(that.borderRouterIp) : that.borderRouterIp != null)
      return false;
    if (borderRouterName != null ? !borderRouterName.equals(that.borderRouterName) : that.borderRouterName != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = borderRouterIp != null ? borderRouterIp.hashCode() : 0;
    result = 31 * result + (borderRouterName != null ? borderRouterName.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("BorderRouterEntity{");
    sb.append("borderRouterIp='").append(borderRouterIp).append('\'');
    sb.append(", borderRouterName='").append(borderRouterName).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
