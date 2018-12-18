package com.ad.server.pojo;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ad.server.Name;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "creative")
@Name("name")
public class Creative implements Serializable {

  private static final long serialVersionUID = 1L;

  public Creative(final String guid, final Integer id) {
    this.accountGuid = guid;
    this.creativeId = id;
  }

  public Creative(
      final Integer id,
      final Integer campaignId,
      final Integer advertiserId,
      final Integer accountId,
      final String accountGuid,
      final String name) {
    this.creativeId = id;
    this.campaignId = campaignId;
    this.advertiserId = advertiserId;
    this.accountId = accountId;
    this.accountGuid = accountGuid;
    this.name = name;
  }

  public Creative(final Integer id, final String byGeo) {
    this.creativeId = id;
    this.byGeo = byGeo;
  }

  @Id
  @Column(name = "creative_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer creativeId;

  @ManyToOne
  @JoinColumn(name = "campaign_id")
  private Campaign campaign;

  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "target_id")
  private Target target;

  @ManyToOne
  @JoinColumn(name = "creative_size_id")
  private CreativeSize creativeSize;

  @ManyToOne
  @JoinColumn(name = "creative_format_id")
  private CreativeFormat creativeFormat;

  @ManyToOne
  @JoinColumn(name = "status_id")
  private Status status;

  @Transient
  private String accountGuid;

  @Transient
  private Integer campaignId;

  @Transient
  private Integer advertiserId;

  @Transient
  private Integer accountId;

  @Transient
  private String byGeo;

}