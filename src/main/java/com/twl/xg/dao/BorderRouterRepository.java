package com.twl.xg.dao;

import com.twl.xg.domain.BorderRouterEntity;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * persistence layer class for <code>BorderRouterEntity</code>. Defines basic database
 * operation methods for border router entity.
 *
 * @see BorderRouterEntity
 * @author Xiaozheng Guo
 * @version 1.0
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Repository
public class BorderRouterRepository {
  @Autowired
  private SessionFactory sessionFactory;

  private static final Logger logger = Logger.getLogger(BorderRouterRepository.class);

  /**
   * Save the input border router in database;
   *
   * Throws <code>NullPointerException</code> if the input borderRouterEntity is null;
   *
   * @param borderRouterEntity The model of  border router you want to save.
   * @return Return the ID of border router entity, in this case, the ID is its
   * IPv6 address.
   */
  public String save(BorderRouterEntity borderRouterEntity) {
    if (borderRouterEntity == null) {
      throw(new NullPointerException("The input borderRouterEntity is null"));
    }
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

  /**
   * Get a <code>BorderRouterEntity</code> instance for the input border router IP.
   *
   * Return <code>null</code> if the input border router IP doesn't exist in database.
   *
   * @param borderRouterIp The IPv6 address of the border router you want to get.
   * @return <code>BorderRouterEntity</code>
   */
  public BorderRouterEntity get(String borderRouterIp) {
    String hql = "FROM BorderRouterEntity WHERE borderRouterIp = '" + borderRouterIp + "'";
    BorderRouterEntity borderRouter = (BorderRouterEntity) sessionFactory
                                .getCurrentSession()
                                .createQuery(hql)
                                .uniqueResult();
    if (borderRouter != null) {
      logger.debug("get:  get BorderRouterEntity, IP = " + borderRouter.getBorderRouterIp());
    } else {
      logger.error("get:  get BorderRouterEntity, border router doesn't exist , ip = " + borderRouterIp);
    }
    return borderRouter;
  }

}
