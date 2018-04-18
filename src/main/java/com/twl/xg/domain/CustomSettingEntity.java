package com.twl.xg.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is a POJO class that map to "custom_setting" table in database.
 */
@Entity
@Table(name = "custom_setting", schema = "house_monitor_db")
public class CustomSettingEntity {
  private String propertyName;
  private String propertyValue;

  // TODO
}
