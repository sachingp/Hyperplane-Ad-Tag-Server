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
@Table (name = "asset_size")
public class AssetSize implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="asset_size_id")
    private Integer assetSizeId;

    @Column (name="size")
    private String size;

}