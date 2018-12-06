package com.ad.server.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "creative_tag")
public class CreativeTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name="creative_tag_id")
    private Integer creativeTagId;

    @Column (name="ad_partner_id")
    private Integer adPartnerId;

    @Column (name="tag_type_id")
    private Integer tagTypeId;

    @Column (name="creative_id")
    private Integer creativeId;

    @Column (name="tag_guid")
    private String tagGuid;

    @Column (name="creative_markup_url")
    private String creativeMarkupUrl;

}