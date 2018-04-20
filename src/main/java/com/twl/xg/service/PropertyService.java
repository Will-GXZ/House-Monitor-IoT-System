package com.twl.xg.service;

import com.twl.xg.dao.PropertyRepository;
import com.twl.xg.domain.PropertyEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class contains methods to save and get properties of the application. These
 * properties are stored in database.
 */
@Service
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class PropertyService {
  @Autowired
  PropertyRepository propertyRepository;

  private static final Logger logger = Logger.getLogger(PropertyService.class);

  /**
   * Get property value by the input property name. If there is no such property,
   * return <code>null</code>;
   */
  @Transactional
  public String getProperty(String propertyName) {
    PropertyEntity propertyEntity = propertyRepository.get(propertyName);
    if (propertyEntity == null) {
      return null;
    } else {
      String propertyValue = propertyEntity.getPropertyValue();
      logger.debug("geteProperty:  key = " + propertyName + ", value = " + propertyValue);
      return propertyValue;
    }
  }

  /**
   * Delete the property by the input propertyName. If the input property name
   * is null or doesn't exist, just ignore it.
   */
  @Transactional
  public void deleteProperty(String propertyName) {
    propertyRepository.delete(propertyName);
  }

  /**
   * Save or update property by the input name and value.
   *
   * @throws NullPointerException if the input property name is null.
   */
  @Transactional
  public void setProperty(String propertyName, String propertyValue) {
    if (propertyName == null) {
      logger.error("setProperty:  The input propertyName is null.");
      throw new NullPointerException("The input propertyName is null.");
    }
    PropertyEntity propertyEntity = new PropertyEntity();
    propertyEntity.setPropertyName(propertyName);
    propertyEntity.setPropertyValue(propertyValue);
    propertyRepository.saveOrUpdate(propertyEntity);
  }

  /**
   * Get all property from database, return a map view of properties. Users should
   * not modify this map.
   *
   * @return A <code>Map</></code> of property name and value pair.
   */
  @Transactional
  public Map<String, String> getPropertyMap() {
    Map<String, String> map = new HashMap<>();
    List propertyList = propertyRepository.getAll();
    for (Object o : propertyList) {
      if (o != null && o.getClass() == PropertyEntity.class) {
        PropertyEntity propertyEntity = (PropertyEntity) o;
        map.put(propertyEntity.getPropertyName(), propertyEntity.getPropertyValue());
      }
    }
    return Collections.unmodifiableMap(map);
  }
}
