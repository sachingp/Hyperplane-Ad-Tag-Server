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
@Table (name = "macros")
public class Macros implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="macros_id")
    private Integer macrosId;

    @Column (name="macro_name")
    private String macroName;

    @Column (name="macro_value")
    private String macroValue;

    @Column (name="account_id")
    private Integer accountId;

    @Column (name="advertiser_id")
    private Integer advertiserId;

    @Column (name="partner_id")
    private Integer partnerId;

    @Column (name="status_id")
    private Integer statusId;

}