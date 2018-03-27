package com.twl.xg.dao;

import com.twl.xg.domain.SensorDataEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Repository
public class SensorDataRepository {
  @Autowired
  private SessionFactory sessionFactory;

  /**
   * Save the input sensor data entity in database.
   * @param sensorDataEntity An instance of <code>SensorDataEntity</code> of the
   *                         sensor data you want to save.
   * @return  Return <code>true</code> on success, return <code>false</code> instead.
   */
  public boolean save(SensorDataEntity sensorDataEntity) {
    return false;
  }

  /**
   * Delete all entries in sensorData table.
   * @return Return <code>true</code> on success, return <code>false</code> instead.
   */
  public boolean clear() {
    return false;
  }

  /**
   * Fetch all entries in sensorData table.
   * @return A list of <code>SensorDataEntity</code>.
   */
  public List<SensorDataEntity> getAll() {
    return null;
  }

  /**
   * Fetch all entries of sensor data for the input sensor IP.
   * @param sensorIp The IPv6 address of the sensor from which the data you want
   *                 to fetch generated.
   * @return A list of <code>SensorDataEntity</code>.
   */
  public List<SensorDataEntity> getAll(String sensorIp) {
    return null;
  }

  // TODO
}
