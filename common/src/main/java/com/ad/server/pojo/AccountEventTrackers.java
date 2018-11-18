package com.ad.server.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "account_event_trackers")
public class AccountEventTrackers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="account_event_tracker_id")
    private Integer accountEventTrackerId;

    @Column (name="event_id")
    private Integer eventId;

    @Column (name="account_id")
    private Integer accountId;

    @Column (name="tracker_url")
    private String trackerUrl;

    @Column (name="append_macros")
    private String appendMacros;

    @Column (name="status_id")
    private Integer statusId;

}