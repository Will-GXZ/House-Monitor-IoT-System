package com.twl.xg.dao;

import com.twl.xg.domain.BorderRouterEntity;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Repository
public class BorderRouterRepository {
  @Autowired
  private SessionFactory sessionFactory;

  private static final Logger logger = Logger.getLogger(BorderRouterRepository.class);

  /**
   * Save the input border router in database;
   * @param borderRouterEntity The model of  border router you want to save.
   * @return Return the ID of border router entity, in this case, the ID is its
   * IPv6 address.
   */
  public String save(BorderRouterEntity borderRouterEntity) {
    String id = (String)sessionFactory.getCurrentSession().save(borderRouterEntity);
    logger.debug("save: saved border router, id = " + id);
    return id;
  }

  /**
   * Fetch all border router from database, ordered by border router name.
   * @return A list of <code>BorderRouterEntity</code>.
   */
  public List<BorderRouterEntity> getAll() {
    String hql = "FROM BorderRouterEntity router ORDER BY router.borderRouterName";
    List<BorderRouterEntity> results = sessionFactory
                                           .getCurrentSession()
                                           .createQuery(hql)
                                           .getResultList();
    logger.debug("getAll: " + results.size() + " entries of border router got");
    return results;
  }

  /**
   * Get the number of borderRouter entries in database.
   * @return The number of entries in database.
   */
  public long size() {
    String hql = "SELECT count(*) FROM BorderRouterEntity";
    long size = (Long)sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();
    logger.debug("size:  The total number of border router entries is " + size);
    return size;
  }

  /**
   * Delete all entries in borderRouter table. Note: This method will also delete
   * all entries in sensor table and sensorData table, because these two tables
   * are delete-cascade.
   */
  public void clear() {
    String hql = "DELETE FROM BorderRouterEntity ";
    int count = sessionFactory.getCurrentSession().createQuery(hql).executeUpdate();
    logger.debug("clear:  " + count + " entries deleted");
  }

}
