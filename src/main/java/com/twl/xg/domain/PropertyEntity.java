package com.twl.xg.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * This is a POJO class that map to "custom_setting" table in database.
 *
 * @see com.twl.xg.dao.PropertyRepository
 */
@Entity
@Table(name = "properties", schema = "house_monitor_db")
public class PropertyEntity {
  private String propertyName;
  private String propertyValue;

  @Id
  @Column(name = "property_name", nullable = false, length = 50)
  public String getPropertyName() {
    return propertyName;
  }

  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }

  @Column(name = "property_value", length = 1000)
  public String getPropertyValue() {
    return propertyValue;
  }

  public void setPropertyValue(String propertyValue) {
    this.propertyValue = propertyValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PropertyEntity that = (PropertyEntity) o;
    return Objects.equals(propertyName, that.propertyName) &&
        Objects.equals(propertyValue, that.propertyValue);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("PropertyEntity{");
    sb.append("propertyName='").append(propertyName).append('\'');
    sb.append(", propertyValue='").append(propertyValue).append('\'');
    sb.append('}');
    return sb.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(propertyName, propertyValue);
  }
}
