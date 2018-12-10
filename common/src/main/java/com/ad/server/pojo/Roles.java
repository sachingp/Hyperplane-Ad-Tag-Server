package com.ad.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Roles implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "role_id")
  private Integer roleId;

  @Column(name = "role_type")
  private String roleType;

  @Column(name = "role_desc")
  private String roleDesc;

}