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
@Table(name = "events")
public class Events implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "event_id")
  private Integer eventId;

  @Column(name = "event_name")
  private String eventName;

  @Column(name = "event_desc")
  private String eventDesc;

}