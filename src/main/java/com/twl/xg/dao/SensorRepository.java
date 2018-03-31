package com.twl.xg.dao;

import com.twl.xg.domain.SensorEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Repository
public class SensorRepository {
  @Autowired
  private SessionFactory sessionFactory;

  private static final Logger logger = Logger.getLogger(SensorRepository.class);

  /**
   * Get a <code>SensorEntity</code> instance for the input sensor IP.
   * Return <code>null</code> if the input sensor IP doesn't exist in database.
   *
   * @param sensorIp The IPv6 address of the sensor you want to get.
   * @return <code>SensorEntity</code>
   */
  public SensorEntity get(String sensorIp) {
    String hql = "FROM SensorEntity s WHERE s.sensorIp = '" + sensorIp + "'";
    SensorEntity sensor = (SensorEntity) sessionFactory
        .getCurrentSession()
        .createQuery(hql)
        .uniqueResult();
    if (sensor != null) {
      logger.debug("get:  get SensorEntity, IP = " + sensor.getSensorIp());
    } else {
      logger.error("get:  get SensorEntity, sensor doesn't exist !, ip = " + sensorIp);
    }
    return sensor;
  }

  /**
   * Get the total number of entries of sensor.
   * @return the size of sensor table.
   */
  public long size() {
    long size = (Long)sessionFactory
        .getCurrentSession()
        .createQuery("SELECT count(*) FROM SensorEntity")
        .uniqueResult();
    logger.debug("size: The total number of sensor entries is: " + size);
    return size;
  }

  /**
   * Save the input sensorEntity in database. Throw a <code>NullPointerException</code>
   * if the input sensorEntity is <code>null</code>.
   *
   * @param sensorEntity Instance of <code>SensorEntity</code> for the sensor
   *                     you want to save.
   * @return return a string of ID of the saved entry
   */
  public String save(SensorEntity sensorEntity) {
    if (sensorEntity == null) {
      throw(new NullPointerException("The input SensorEntity to save is null"));
    }
    Session session = sessionFactory.getCurrentSession();
    Serializable id = session.save(sensorEntity);
    logger.debug("save: A sensorEntity saved, id = " + id);
    return id.toString();
  }

  /**
   * Delete all entries in sensor table. Note: this operation will also delete
   * all entries in sensorData table, because sensorData table is delete-cascade.
   */
  public void clear() {
    String hql = "DELETE FROM SensorEntity";
    Query query = sessionFactory.getCurrentSession().createQuery(hql);
    int count = query.executeUpdate();
    logger.debug(String.format("clear: table '%s' cleared, %d rows deleted", SensorRepository.class, count));
  }

  /**
   * Fetch all entries in sensor table, ordered by sensor name.
   * @return A list of <code>SensorEntity</code>.
   */
  public List<SensorEntity> getAll() {
    String hql = "FROM SensorEntity s ORDER BY s.sensorName";
    Query query = sessionFactory.getCurrentSession().createQuery(hql);
    List<SensorEntity> results = query.getResultList();
    logger.debug("getAll: " + results.size() + " entries got in total");
    return results;
  }

  /**
   * Fetch all entries of sensors that connected to the input border router.
   *
   * Return an empty list if the input <code>borderRouterIp</code> doesn't exist
   * in database.
   *
   * @param borderRouterIp The IPv6 address of the border router to which the
   *                       sensors are connected.
   * @return A list of <code>SensorEntity</code>.
   */
  public List<SensorEntity> getAll(String borderRouterIp) {
    String hql = String.format("FROM SensorEntity s WHERE s.borderRouterByBorderRouterIp.borderRouterIp = '%s' ORDER BY s.sensorName", borderRouterIp);
    Query query = sessionFactory.getCurrentSession().createQuery(hql);
    List<SensorEntity> results = query.getResultList();
    logger.debug("getAll: " + results.size() + " entries got in total for border router ip: " + borderRouterIp);
    return results;
  }
}
