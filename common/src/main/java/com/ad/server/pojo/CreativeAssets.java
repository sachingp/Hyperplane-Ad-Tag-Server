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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "creative_assets")
public class CreativeAssets implements Serializable {

  private static final long serialVersionUID = 1L;

  public CreativeAssets(
      final Integer creativeId,
      final String assetUrl,
      final String clickUrl,
      final Integer assetTypeId,
      final String type,
      final Integer assetSizeId,
      final String size) {
    this.creativeId = creativeId;
    this.assetUrl = assetUrl;
    this.clickUrl = clickUrl;
    this.assetTypeId = assetTypeId;
    this.type = type;
    this.assetSizeId = assetSizeId;
    this.size = size;
  }

  @Id
  @Column(name = "creative_asset_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer creativeAssetId;

  @ManyToOne
  @JoinColumn(name = "creative_id")
  private Creative creative;

  @ManyToOne
  @JoinColumn(name = "asset_size_id")
  private AssetSize assetSize;

  @ManyToOne
  @JoinColumn(name = "asset_type_id")
  private AssetType assetType;

  @Column(name = "asset_url")
  private String assetUrl;

  @Column(name = "click_url")
  private String clickUrl;

  @ManyToOne
  @JoinColumn(name = "status_id")
  private Status status;

  @Transient
  private Integer creativeId;

  @Transient
  private Integer assetTypeId;

  @Transient
  private Integer assetSizeId;

  @Transient
  private String type;

  @Transient
  private String size;

}