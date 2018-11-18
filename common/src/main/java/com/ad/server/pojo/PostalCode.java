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
@Table (name = "postal_code")
public class PostalCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="postal_code_id")
    private Integer postalCodeId;

    @Column (name="city_id")
    private Integer cityId;

    @Column (name="postal_code")
    private String postalCode;

}