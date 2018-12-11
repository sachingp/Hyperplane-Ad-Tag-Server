package com.ad.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

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
@Table(name = "account")
@RepositoryRestResource(collectionResourceRel = "account", path = "accounts")
public class Account implements Serializable {

  private static final long serialVersionUID = 1L;

  public Account(final String guid, final Integer id) {
    accountGuid = guid;
    accountId = id;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "account_id")
  private Integer accountId;

  @Column(name = "account_guid")
  private String accountGuid;

  @ManyToOne
  @JoinColumn(name = "account_type_id")
  @RestResource(path = "accountType", rel = "accountType")
  private AccountType accountType;

  @Column(name = "account_name")
  private String accountName;

  @Column(name = "account_email")
  private String accountEmail;

  @Column(name = "account_website")
  private String accountWebsite;

  @ManyToOne
  @JoinColumn(name = "address_id")
  @RestResource(path = "accountAddress", rel = "address")
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