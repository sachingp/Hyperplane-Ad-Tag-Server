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
@Table (name = "account")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="account_id")
    private Integer accountId;

    @Column (name="account_guid")
    private String accountGuid;

    @Column (name="account_type_id")
    private Integer accountTypeId;

    @Column (name="account_name")
    private String accountName;

    @Column (name="account_email")
    private String accountEmail;

    @Column (name="account_website")
    private String accountWebsite;

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