package com.ad.server.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @GeneratedValue(strategy = GenerationType.AUTO)    
    private Integer campaignId;

    @ManyToOne
    @JoinColumn (name="advertiser_id")
    private Advertiser advertiser;

    @Column (name="campaign_name")
    private String campaignName;

    @ManyToOne
    @JoinColumn (name="objective_type_id")
    private ObjectiveType objectiveType;

    @Column (name="custom_attrbutes")
    private String customAttributes;

    @ManyToOne
    @JoinColumn (nullable = true, name="target_id")
    private Target target;

    @Column (name="start_date")
    private Date startDate;

    @Column (name="end_date")
    private Date endDate;

    @Column (name="created_date")
    private Date createdDate;

    @Column (name="last_updated_date")
    private Date lastUpdatedDate;

    @ManyToOne
    @JoinColumn (name="status_id")
    private Status status;

}