package com.ad.server.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table (name = "creative_assets")
public class CreativeAssets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="creative_asset_id")
    private Integer creativeAssetId;

    @ManyToOne
    @JoinColumn (name="creative_id")
    private Creative creative;

    @ManyToOne
    @JoinColumn (name="asset_size_id")
    private AssetSize assetSize;

    @ManyToOne
    @JoinColumn (name="asset_type")
    private AssetType assetType;

    @Column (name="asset_url")
    private String assetUrl;

    @ManyToOne
    @JoinColumn (name="status_id")
    private Status status;

}