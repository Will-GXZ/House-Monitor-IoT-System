package com.twl.xg.dao;

import com.twl.xg.domain.SensorDataEntity;
import com.twl.xg.domain.SensorEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Repository
public class SensorDataRepository {
  @Autowired
  private SessionFactory sessionFactory;

  private static final Logger logger = Logger.getLogger(SensorDataRepository.class);

  /**
   * Get the total number of sensor data entries in database.
   * @return the number of sensor data entries in database.
   */
  public long size() {
    long size = (Long)sessionFactory
        .getCurrentSession()
        .createQuery("SELECT count(*) FROM SensorDataEntity")
        .uniqueResult();
    logger.debug("size: The total number of sensor data entries is: " + size);
    return size;
  }

  /**
   * Save the input sensor data entity in database.
   *
   * Throws <code>NullPointerException</code> when the input
   * <code>sensorDataEntity</code> is null;
   *
   * @param sensorDataEntity An instance of <code>SensorDataEntity</code> of the
   *                         sensor data you want to save.
   * @return  Return the ID of the added sensor data entry.
   */
  public String save(SensorDataEntity sensorDataEntity) {
    if (sensorDataEntity == null) {
      throw(new NullPointerException("The input sensorDataEntity to save is null"));
    }
    Session session = sessionFactory.getCurrentSession();
    Serializable id = session.save(sensorDataEntity);
    logger.debug("save:  new sensor data saved, id = " + id.toString());
    return id.toString();
  }

  /**
   * Delete all entries in sensorData table. return the number of entries that have
   * been deleted.
   *
   * @return number of entries deleted.
   */
  public int clear() {
    String hql = "DELETE FROM SensorDataEntity";
    Query query = sessionFactory.getCurrentSession().createQuery(hql);
    int count = query.executeUpdate();
    logger.debug(String.format("clear: table '%s' cleared, %d rows deleted", SensorDataEntity.class, count));
    return count;
  }

  /**
   * Fetch all entries in sensorData table. Ordered by automatically generated id.
   * @return A list of <code>SensorDataEntity</code>.
   */
  public List<SensorDataEntity> getAll() {
    String hql = "FROM SensorDataEntity data ORDER BY data.id";
    Query query = sessionFactory.getCurrentSession().createQuery(hql);
    List<SensorDataEntity> results = query.getResultList();
    logger.debug("getAll: " + results.size() + " entries of sensor data got");
    return results;
  }

  /**
   * Fetch all entries of sensor data for the input sensor IP. The results are
   * be ordered by automatically generated ID of sensor data entries.
   *
   * Return an empty list if the input <code>sensorIp</code> doesn't exist in the
   * database.
   *
   * @param sensorIp The IPv6 address of the sensor from which the data you want
   *                 to fetch generated.
   * @return A list of <code>SensorDataEntity</code>.
   */
  public List<SensorDataEntity> getAll(String sensorIp) {
    String hql = "FROM SensorDataEntity data WHERE data.sensorIp = '" + sensorIp + "' ORDER BY data.id";
    Query query = sessionFactory.getCurrentSession().createQuery(hql);
    List<SensorDataEntity> results = query.getResultList();
    logger.debug("getAll:  " + results.size() + " entries got for sensor ip = " + sensorIp);
    return results;
  }

  /**
   * Get all entries of sensor data that were created later than the input timeStamp.
   * The results are ordered by sensor data ID.
   *
   * Throw <code>NullPointerException</code> if the input <code>timeStamp</code> is null;
   *
   * @param timeStamp The time stamp you want to query
   * @return A list of <code>SensorDataEntity</code>
   */
  public List<SensorDataEntity> getAllLaterThan(Date timeStamp) {
    if (timeStamp == null) {
      throw(new NullPointerException("The input timeStamp is null"));
    }
    String hql = "FROM SensorDataEntity WHERE timeStamp >= :timeStamp ORDER BY id";
    Query query = sessionFactory
                  .getCurrentSession()
                  .createQuery(hql)
                  .setParameter("timeStamp", timeStamp);
    List<SensorDataEntity> results = query.getResultList();
    logger.debug("getAllLaterThan:  get " + results.size() + " data entries later than " + timeStamp);
    for (SensorDataEntity data : results) {
      logger.debug("getAllLaterThan: data --> " + data);
    }
    return results;
  }

  /**
   * Get all entries of sensor data that were created later than the input timeStamp
   * for the input sensor IP. The results are ordered by sensor data ID.
   *
   * Return an empty list if the input <code>sensorIp</code> doesn't exist.
   *
   * Throws <code>NullPointerException</code> if the input <code>timeStamp</code> is null;
   *
   * @param timeStamp The time stamp you want to query.
   * @return A list of <code>SensorDataEntity</code>
   */
  public List<SensorDataEntity> getAllLaterThan(String sensorIp, Date timeStamp) {
    if (timeStamp == null) {
      throw(new NullPointerException("The input timeStamp is null"));
    }
    String hql = "FROM SensorDataEntity WHERE timeStamp >= :timeStamp AND sensorIp = :sensorIp ORDER BY id";
    Query query = sessionFactory
        .getCurrentSession()
        .createQuery(hql)
        .setParameter("timeStamp", timeStamp)
        .setParameter("sensorIp", sensorIp);
    List<SensorDataEntity> results = query.getResultList();
    logger.debug("getAllLaterThan:  get " + results.size() + " data entries later than" + timeStamp + " for sensor IP = " + sensorIp);
    for (SensorDataEntity data : results) {
      logger.debug("getAllLaterThan: data--> " + data);
    }
    return results;
  }
}
