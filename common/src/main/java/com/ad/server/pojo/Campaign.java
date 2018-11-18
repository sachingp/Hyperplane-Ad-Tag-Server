package com.ad.server.pojo;

import java.io.Serializable;
import java.util.Date;

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
@Table (name = "campaign")
public class Campaign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="campaign_id")
    private Integer campaignId;

    @Column (name="advertiser_id")
    private Integer advertiserId;

    @Column (name="campaign_name")
    private String campaignName;

    @Column (name="objective_type_id")
    private Integer objectiveTypeId;

    @Column (name="custom_attrbutes")
    private String customAttrbutes;

    @Column (name="target_id")
    private Integer targetId;

    @Column (name="start_date")
    private Date startDate;

    @Column (name="end_date")
    private Date endDate;

    @Column (name="created_date")
    private Date createdDate;

    @Column (name="last_updated_date")
    private Date lastUpdatedDate;

    @Column (name="status_id")
    private Integer statusId;

}