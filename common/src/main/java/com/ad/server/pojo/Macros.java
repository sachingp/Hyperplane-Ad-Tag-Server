package com.ad.server.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
    @JoinColumn (name="account_id")
    private Account account;

    @ManyToOne
    @JoinColumn (name="advertiser_id")
    private Advertiser advertiser;

    @ManyToOne
    @JoinColumn (name="partner_id")
    private AdPartner partner;

    @ManyToOne
    @JoinColumn (name="status_id")
    private Status status;

}