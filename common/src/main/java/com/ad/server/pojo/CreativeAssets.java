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
@Table (name = "creative_assets")
public class CreativeAssets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="creative_asset_id")
    private Integer creativeAssetId;

    @Column (name="creative_id")
    private Integer creativeId;

    @Column (name="asset_size_id")
    private Integer assetSizeId;

    @Column (name="asset_type")
    private Integer assetType;

    @Column (name="asset_url")
    private String assetUrl;

    @Column (name="status_id")
    private Integer statusId;

}