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
@Table (name = "segment")
public class Segment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="segment_id")
    private Integer segmentId;

    @Column (name="segment_name")
    private String segmentName;

    @Column (name="partner_name")
    private Integer partnerName;

    @Column (name="partner_segment_taxonomy_id")
    private String partnerSegmentTaxonomyId;

    @Column (name="status_id")
    private Integer statusId;

}