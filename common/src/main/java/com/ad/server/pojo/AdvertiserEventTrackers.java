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
@Table (name = "advertiser_event_trackers")
public class AdvertiserEventTrackers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="advertiser_tracker_id")
    private Integer advertiserTrackerId;

    @Column (name="event_id")
    private Integer eventId;

    @Column (name="advertiser_id")
    private Integer advertiserId;

    @Column (name="tracker_url")
    private String trackerUrl;

    @Column (name="append_macros")
    private String appendMacros;

    @Column (name="status_id")
    private Integer statusId;

}