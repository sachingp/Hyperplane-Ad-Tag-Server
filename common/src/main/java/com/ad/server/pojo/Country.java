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
@Table (name = "country")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="country_id")
    private Integer countryId;

    @Column (name="geoname_id")
    private String geonameId;

    @Column (name="region_id")
    private String regionId;

    @Column (name="currency_id")
    private String currencyId;

    @Column (name="country_code")
    private String countryCode;

    @Column (name="country_code_2")
    private String countryCode2;

    @Column (name="country_name")
    private String countryName;

    @Column (name="local_name")
    private String localName;

}