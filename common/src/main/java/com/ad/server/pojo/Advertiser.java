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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ad.server.Name;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "advertiser")
@Name("advertiserName")
public class Advertiser implements Serializable {

  private static final long serialVersionUID = 1L;

  public Advertiser(final Integer id, final String name) {
    this.advertiserId = id;
    this.advertiserName = name;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "advertiser_id")
  private Integer advertiserId;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

  @Column(name = "advertiser_name")
  private String advertiserName;

  @Column(name = "advertiser_email")
  private String advertiserEmail;

  @Column(name = "advertiser_website")
  private String advertiserWebsite;

  @ManyToOne
  @JoinColumn(name = "address_id")
  private Address address;

  @Column(name = "logo_url")
  private String logoUrl;

  @Column(name = "time_zone_id")
  private Short timeZoneId;

  @Column(name = "currency_id")
  private Short currencyId;

  @ManyToOne
  @JoinColumn(name = "status_id")
  private Status status;

}