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
@Table (name = "asset_type")
public class AssetType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="asset_type_id")
    private Integer assetTypeId;

    @Column (name="type")
    private String type;

    @Column (name="ext")
    private String ext;

    @Column (name="mime")
    private String mime;

}