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
@Table (name = "ad_partner")
public class AdPartner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="ad_partner_id")
    private Integer adPartnerId;

    @Column (name="name")
    private String name;

    @Column (name="address_id")
    private Integer addressId;

    @Column (name="email")
    private String email;

    @Column (name="ad_partner_type_id")
    private Integer adPartnerTypeId;

    @Column (name="status_id")
    private Integer statusId;

}