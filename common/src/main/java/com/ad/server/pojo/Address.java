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
@Table(name = "address")
public class Address implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "address_id")
  private Integer addressId;

  @ManyToOne
  @JoinColumn(name = "address_type_id")
  private AddressType addressType;

  @Column(name = "address_line1")
  private String addressLine1;

  @Column(name = "address_line2")
  private String addressLine2;

  @Column(name = "country_id")
  private Integer countryId;

  @Column(name = "state_id")
  private Integer stateId;

  @Column(name = "city_id")
  private Integer cityId;

  @Column(name = "postal_code_id")
  private Integer postalCodeId;

}