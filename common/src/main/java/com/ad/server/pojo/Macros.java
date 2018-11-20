package com.ad.server.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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

    @ManyToOne
    @Column (name="account_id")
    private Account account;

    @ManyToOne
    @Column (name="advertiser_id")
    private Advertiser advertiser;

    @ManyToOne
    @Column (name="partner_id")
    private AdPartner partner;

    @ManyToOne
    @Column (name="status_id")
    private Status status;

}