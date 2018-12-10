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
@Table(name = "billing_type")
public class BillingType implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "billing_type_id")
  private Integer billingTypeId;

  @Column(name = "billing_type_label")
  private String billingTypeLabel;

}