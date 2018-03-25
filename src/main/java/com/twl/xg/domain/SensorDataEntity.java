package com.twl.xg.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sensorData", schema = "testdb2")
public class SensorDataEntity {
  private int id;
  private String sensorIp;
  private Date timeStamp;
  private double temperature;
  private double humidity;
  private double lightness;
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
  @Column(name = "sensorIp", nullable = false, insertable = false, updatable = false, length = 30)
  public String getSensorIp() {
    return sensorIp;
  }

  public void setSensorIp(String sensorIp) {
    this.sensorIp = sensorIp;
  }

  @Basic
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "timeStamp", updatable = false)
  public Date getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

  @Basic
  @Column(name = "temperature", nullable = false)
  public double getTemperature() {
    return temperature;
  }

  public void setTemperature(double temperature) {
    this.temperature = temperature;
  }

  @Basic
  @Column(name = "humidity", nullable = false)
  public double getHumidity() {
    return humidity;
  }

  public void setHumidity(double humidity) {
    this.humidity = humidity;
  }

  @Basic
  @Column(name = "lightness", nullable = false)
  public double getLightness() {
    return lightness;
  }

  public void setLightness(double lightness) {
    this.lightness = lightness;
  }

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "sensorIp", referencedColumnName = "sensorIp", nullable = false)
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

    if (id != that.id) return false;
    if (temperature != that.temperature) return false;
    if (humidity != that.humidity) return false;
    if (lightness != that.lightness) return false;
    if (! sensorBySensorIp.equals(that.sensorBySensorIp)) return false;
    if (timeStamp != null ? !timeStamp.equals(that.timeStamp) : that.timeStamp != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (timeStamp != null ? timeStamp.hashCode() : 0);
    result = 31 * result + (int)temperature;
    result = 31 * result + (int)humidity;
    result = 31 * result + (int)lightness;
    result = 31 * result + sensorBySensorIp.hashCode();
    return result;
  }
}
