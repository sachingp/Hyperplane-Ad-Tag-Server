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
@Table (name = "billing_cycle")
public class BillingCycle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="billing_cycle_id")
    private Integer billingCycleId;

    @Column (name="billing_cycle_desc")
    private String billingCycleDesc;

}