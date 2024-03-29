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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "creative_tag")
public class CreativeTag implements Serializable {

  private static final long serialVersionUID = 1L;

  public CreativeTag(final String guid, final Integer creativeId, final Integer tagTypeId, final Integer adPartnerId) {
    this.tagGuid = guid;
    this.creativeId = creativeId;
    this.tagTypeId = tagTypeId;
    this.adPartnerId = adPartnerId;
  }

  public CreativeTag(
      final String guid,
      final Integer creativeId,
      final Integer tagTypeId,
      final Integer adPartnerId,
      final String macroName,
      final String macroValue) {
    this.tagGuid = guid;
    this.creativeId = creativeId;
    this.tagTypeId = tagTypeId;
    this.adPartnerId = adPartnerId;
    this.macroName = macroName;
    this.macroValue = macroValue;
  }

  public CreativeTag(final String guid, final String url) {
    this.tagGuid = guid;
    this.creativeMarkupUrl = url;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "creative_tag_id")
  private Integer creativeTagId;

  @Column(name = "ad_partner_id")
  private Integer adPartnerId;

  @Column(name = "tag_type_id")
  private Integer tagTypeId;

  @ManyToOne
  @JoinColumn(name = "creative_id")
  private Creative creative;

  @Column(name = "tag_guid")
  private String tagGuid;

  @Column(name = "creative_markup_url")
  private String creativeMarkupUrl;

  @Transient
  private Integer creativeId;

  @Transient
  private String macroName;

  @Transient
  private String macroValue;

}