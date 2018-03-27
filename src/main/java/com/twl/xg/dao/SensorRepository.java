package com.twl.xg.dao;

import com.twl.xg.domain.SensorEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Repository
public class SensorRepository {
  @Autowired
  private SessionFactory sessionFactory;

  /**
   * Save the input sensorEntity in database.
   * @param sensorEntity Instance of <code>SensorEntity</code> for the sensor
   *                     you want to save.
   * @return Return <code>true</code> on success, <code>false</code> otherwise.
   */
  public boolean save(SensorEntity sensorEntity) {
    return false;
  }

  /**
   * Delete all entries in sensor table. Note: this operation will also delete
   * all entries in sensorData table, because sensorData table is delete-cascade.
   * @return Return <code>true</code> on success, <code>false</code> otherwise.
   */
  public boolean clear() {
    return false;
  }

  /**
   * Fetch all entries in sensor table.
   * @return A list of <code>SensorEntity</code>.
   */
  public List<SensorEntity> getAll() {
    return null;
  }

  /**
   * Fetch all entries of sensors that connected to the input border router.
   * @param borderRouterIp The IPv6 address of the border router to which the
   *                       sensors are connected.
   * @return A list of <code>SensorEntity</code>.
   */
  public List<SensorEntity> getAll(String borderRouterIp) {
    return null;
  }

  // TODO
}
