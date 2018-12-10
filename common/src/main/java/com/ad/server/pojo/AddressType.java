package com.ad.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address_type")
public class AddressType implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "address_type_id")
  private Integer addressTypeId;

  @Column(name = "address_type_desc")
  private String addressTypeDesc;

}