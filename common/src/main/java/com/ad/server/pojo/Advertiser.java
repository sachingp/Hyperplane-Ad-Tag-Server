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
@Table (name = "advertiser")
public class Advertiser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="advertiser_id")
    private Integer advertiserId;

    @Column (name="account_id")
    private Integer accountId;

    @Column (name="advertiser_name")
    private String advertiserName;

    @Column (name="advertiser_email")
    private String advertiserEmail;

    @Column (name="advertiser_website")
    private String advertiserWebsite;

    @Column (name="address_id")
    private Integer addressId;

    @Column (name="logo_url")
    private String logoUrl;

    @Column (name="time_zone_id")
    private Short timeZoneId;

    @Column (name="currency_id")
    private Short currencyId;

    @Column (name="status_id")
    private Integer statusId;

}