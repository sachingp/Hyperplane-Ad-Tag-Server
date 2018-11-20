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
@Table (name = "state")
public class State implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="state_id")
    private Integer stateId;

    @Column (name="state_unique_id")
    private Integer stateUniqueId;

    @ManyToOne
    @Column (name="country_id")
    private Country country;

    @Column (name="state_long_namw")
    private String stateLongNamw;

    @Column (name="state_code")
    private String stateCode;

    @Column (name="fips_code")
    private String fipsCode;

    @Column (name="time_zone_id")
    private String timeZoneId;

}