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
@Table (name = "billing")
public class Billing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="billing_id")
    private Integer billingId;

    @Column (name="billing_owner_id")
    private Integer billingOwnerId;

    @Column (name="billing_payment_id")
    private Integer billingPaymentId;

    @Column (name="status_id")
    private Integer statusId;

}