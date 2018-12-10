package com.ad.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account_event_trackers")
public class AccountEventTrackers implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "account_event_tracker_id")
  private Integer accountEventTrackerId;

  @ManyToOne
  @JoinColumn(name = "event_id")
  private Events event;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

  @Column(name = "tracker_url")
  private String trackerUrl;

  @Column(name = "append_macros")
  private String appendMacros;

  @ManyToOne
  @JoinColumn(name = "status_id")
  private Status status;

}