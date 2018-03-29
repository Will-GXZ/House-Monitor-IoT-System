package com.twl.xg.dao;

import com.twl.xg.domain.SensorDataEntity;
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
   * @param sensorDataEntity An instance of <code>SensorDataEntity</code> of the
   *                         sensor data you want to save.
   * @return  Return the ID of the added sensor data entry.
   */
  public String save(SensorDataEntity sensorDataEntity) {
    Session session = sessionFactory.getCurrentSession();
    Serializable id = session.save(sensorDataEntity);
    logger.debug("save:  new sensor data saved, id = " + id.toString());
    return id.toString();
  }

  /**
   * Delete all entries in sensorData table.
   */
  public void clear() {
    String hql = "DELETE FROM SensorDataEntity";
    Query query = sessionFactory.getCurrentSession().createQuery(hql);
    int count = query.executeUpdate();
    logger.debug(String.format("clear: table '%s' cleared, %d rows deleted", SensorDataEntity.class, count));
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
}
