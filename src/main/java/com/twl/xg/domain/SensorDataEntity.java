package com.twl.xg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

/**
 * Object model class for sensor data.
 *
 * @see com.twl.xg.dao.SensorDataRepository
 * @see SensorWrapper
 */
@JsonIgnoreProperties(value = {"sensorBySensorIp"})
@Entity
@Table(name = "sensor_data", schema = "house_monitor_db")
public class SensorDataEntity {
  /**
   * must be less than the maximum column length of VARCHAR field in mySQL.
   */
  private static final int MAX_DATA_JSON_LEN = 1000;

  private int id;
  private String sensorIp;
  private Date timeStamp;
  @JsonRawValue
  private String dataJson;
  private SensorEntity sensorBySensorIp;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", updatable = false)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Basic
  @Column(name = "sensor_ip", nullable = false, insertable = false, updatable = false, length = 256)
  public String getSensorIp() {
    return sensorIp;
  }

  public void setSensorIp(String sensorIp) {
    this.sensorIp = sensorIp;
  }

  @Basic
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "time_stamp", updatable = false)
  public Date getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

  @Basic
  @Column(name = "data_json", nullable = false, length = MAX_DATA_JSON_LEN)
  public String getDataJson() {
    return dataJson;
  }

  public void setDataJson(String dataJson) {
    this.dataJson = dataJson;
  }

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "sensor_ip", referencedColumnName = "sensor_ip", nullable = false)
  public SensorEntity getSensorBySensorIp() {
    return sensorBySensorIp;
  }

  public void setSensorBySensorIp(SensorEntity sensorBySensorIp) {
    this.sensorBySensorIp = sensorBySensorIp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SensorDataEntity that = (SensorDataEntity) o;

    JsonParser parser = new JsonParser();
    JsonElement thisJsonElement = parser.parse(this.dataJson);
    JsonElement thatJsonElement = parser.parse(that.dataJson);
    if (! thisJsonElement.equals(thatJsonElement)) return false;

    if (id != that.id) return false;
    if (! sensorBySensorIp.equals(that.sensorBySensorIp)) return false;
    if (timeStamp != null ? !timeStamp.equals(that.timeStamp) : that.timeStamp != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (timeStamp != null ? timeStamp.hashCode() : 0);
    result = 31 * result + sensorBySensorIp.hashCode();

    JsonParser parser = new JsonParser();
    JsonElement o = parser.parse(dataJson);
    result = 31 * result + o.hashCode();
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("SensorDataEntity{");
    sb.append("id=").append(id);
    sb.append(", sensorIp='").append(sensorIp).append('\'');
    sb.append(", timeStamp=").append(timeStamp);
    sb.append(", dataJson='").append(dataJson).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
