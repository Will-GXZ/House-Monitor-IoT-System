package com.twl.xg.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * This class is just for testing Hibernate framework
 */
@Entity
@Table(name = "user", schema = "testdb1")
public class UserEntity {
  private int uid;
  private Timestamp createTime;
  private String email;
  private String username;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "uid", nullable = false)
  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  @Basic
  @Column(name = "create_time", nullable = true)
  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  @Basic
  @Column(name = "email", nullable = true, length = 255)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Basic
  @Column(name = "username", nullable = false, length = 16)
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserEntity that = (UserEntity) o;

    if (uid != that.uid) return false;
    if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
    if (email != null ? !email.equals(that.email) : that.email != null) return false;
    return username != null ? username.equals(that.username) : that.username == null;
  }

  @Override
  public int hashCode() {
    int result = uid;
    result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (username != null ? username.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    String sb = "UserEntity{" + "uid=" + uid +
        ", createTime=" + createTime +
        ", email='" + email + '\'' +
        ", username='" + username + '\'' +
        '}';
    return sb;
  }
}
