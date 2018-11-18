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
@Table (name = "creative")
public class Creative implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="creative_id")
    private Integer creativeId;

    @Column (name="campaign_id")
    private Integer campaignId;

    @Column (name="name")
    private String name;

    @Column (name="target_id")
    private Integer targetId;

    @Column (name="creative_size_id")
    private Integer creativeSizeId;

    @Column (name="creative_format_id")
    private Integer creativeFormatId;

    @Column (name="status_id")
    private Integer statusId;

}