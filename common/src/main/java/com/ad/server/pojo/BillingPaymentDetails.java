package com.ad.server.pojo;

import java.io.Serializable;
import java.util.Date;

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
@Table (name = "billing_payment_details")
public class BillingPaymentDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="billing_payment_details_id")
    private Integer billingPaymentDetailsId;

    @Column (name="billing_id")
    private Integer billingId;

    @Column (name="billing_type_id")
    private Integer billingTypeId;

    @Column (name="bank_name")
    private String bankName;

    @Column (name="bank_account_number")
    private Integer bankAccountNumber;

    @Column (name="bank_routing_number")
    private Integer bankRoutingNumber;

    @Column (name="credit_card_number")
    private Integer creditCardNumber;

    @Column (name="credit_card_expiry_date")
    private String creditCardExpiryDate;

    @Column (name="credit_card_cvv")
    private String creditCardCvv;

    @Column (name="billing_cycle_id")
    private Integer billingCycleId;

    @Column (name="billing_date")
    private Date billingDate;

}