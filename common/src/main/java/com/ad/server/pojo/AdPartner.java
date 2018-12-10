package com.ad.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ad_partner")
public class AdPartner implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ad_partner_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer adPartnerId;

  @Column(name = "name")
  private String name;

  @Column(name = "address_id")
  private Integer addressId;

  @Column(name = "email")
  private String email;

  @ManyToOne
  @JoinColumn(name = "ad_partner_type_id")
  private AdPartnerType adPartnerType;

  @ManyToOne
  @JoinColumn(name = "status_id")
  private Status status;

}