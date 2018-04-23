package com.twl.xg.dao;

import com.twl.xg.domain.PropertyEntity;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This is the persistence layer class of <code>PropertyEntity</code>. Defines some basic
 * operations on database.
 *
 * @see PropertyEntity
 * @author Xiaozheng Guo
 * @version 1.0
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Repository
public class PropertyRepository {
  @Autowired
  private SessionFactory sessionFactory;

  private static final Logger logger = Logger.getLogger(PropertyRepository.class);

  /**
   * Store the input <code>PropertyEntity</code> instance in database. If the
   * property name is already exist, update it.
   */
  public void saveOrUpdate(PropertyEntity propertyEntity) {
    if (propertyEntity == null) {
      throw new NullPointerException("The input PropertyEntity object is null");
    }
    sessionFactory.getCurrentSession().saveOrUpdate(propertyEntity.getPropertyName(), propertyEntity);
    logger.debug("save:  Save propertyEntity --> " + propertyEntity);
  }

  /**
   * Delete an property for the input property name. After this operation, ensure
   * there is no property with the input name exists in database. If the input
   * propertyName is null, just ignore.
   *
   * @param propertyName The name of the property.
   */
  public void delete(String propertyName) {
    if (propertyName == null) {
      logger.error("delete:  The input propertyName is null.");
      return;
    }
    String hql = "DELETE FROM PropertyEntity WHERE propertyName='" + propertyName + "'";
    int count = sessionFactory.getCurrentSession().createQuery(hql).executeUpdate();
    logger.debug("delete:  Deleted property with name --> " + propertyName
        + ", count = " + count);
  }

  /**
   * Get the property entity for the input property name.
   *
   * @return An instance of <code>PropertyEntity</code>. If the input name is null or
   * there is no such property in database, return <code>null</code>.
   */
  public PropertyEntity get(String propertyName) {
    if (propertyName == null) {
      logger.error("get:  The input propertyName is null");
      return null;
    }
    String hql = "FROM PropertyEntity WHERE propertyName='" + propertyName + "'";
    PropertyEntity result = (PropertyEntity) sessionFactory
        .getCurrentSession().createQuery(hql).uniqueResult();
    logger.debug("get:  PropertyEntity --> " + result);
    return result;
  }

  /**
   * Get all properties in database.
   *
   * @return A list of <code>PropertyEntity</code> instances.
   */
  public List getAll() {
    String hql = "FROM PropertyEntity";
    List results = sessionFactory.getCurrentSession().createQuery(hql).getResultList();
    logger.debug("getAll: Properties --> " + results.toString());
    return results;
  }
}
