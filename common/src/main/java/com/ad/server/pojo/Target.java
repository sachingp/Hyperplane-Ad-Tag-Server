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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "target")
public class Target implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "target_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer targetId;

  @Column(name = "target_name")
  private String targetName;

  @Column(name = "by_os")
  private String byOs;

  @Column(name = "by_day_part")
  private String byDayPart;

  @Column(name = "by_segment")
  private String bySegment;

  @Column(name = "by_geo")
  private String byGeo;

}