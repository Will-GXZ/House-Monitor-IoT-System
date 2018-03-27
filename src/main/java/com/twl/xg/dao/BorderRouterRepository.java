package com.twl.xg.dao;

import com.twl.xg.domain.BorderRouterEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Repository
public class BorderRouterRepository {
  @Autowired
  private SessionFactory sessionFactory;

  /**
   * Save the input border router in database;
   * @param borderRouterEntity The model of  border router you want to save.
   * @return Return <code>true</code> on success, return <code>false</code> otherwise.
   */
  public boolean save(BorderRouterEntity borderRouterEntity) {
    return false;
  }

  /**
   * Fetch all border router from database.
   * @return A list of <code>BorderRouterEntity</code>.
   */
  public List<BorderRouterEntity> getAll() {
    return null;
  }

  /**
   * Get the number of borderRouter entries in database.
   * @return The number of entries in database.
   */
  public int size() {
    return 0;
  }

  /**
   * Delete all entries in borderRouter table. Note: This method will also delete
   * all entries in sensor table and sensorData table, because these two tables
   * are delete-cascade.
   * @return <code>true</code> on success, return <code>false</code> otherwise.
   */
  public boolean clear() {
    return false;
  }

  // TODO
}
