package com.ad.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "postal_code")
public class PostalCode implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "postal_code_id")
  private Integer postalCodeId;

  @ManyToOne
  @JoinColumn(name = "city_id")
  private City city;

  @Column(name = "postal_code")
  private String postalCode;

}