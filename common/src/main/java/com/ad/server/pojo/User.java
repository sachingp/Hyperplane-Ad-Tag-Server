package com.ad.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
@XmlRootElement
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer userId;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "password_salt")
  private String passwordSalt;

  @Column(name = "profile_photo_file_manager_id")
  private String profilePhotoFileManagerId;

  @Column(name = "locale_id")
  private String localeId;

  @Column(name = "last_login_ip_address")
  private String lastLoginIpAddress;

  @Column(name = "last_login_timestamp")
  private String lastLoginTimestamp;

}