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
@Table(name = "os")
public class Os implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "os_id")
  private Integer osId;

  @Column(name = "os_name")
  private String osName;

  @Column(name = "os_desc")
  private String osDesc;

}