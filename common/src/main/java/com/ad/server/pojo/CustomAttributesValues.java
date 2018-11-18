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
@Table (name = "custom_attributes_values")
public class CustomAttributesValues implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="custom_attributes_values_id")
    private Integer customAttributesValuesId;

    @Column (name="key_id")
    private Integer keyId;

    @Column (name="value")
    private String value;

}