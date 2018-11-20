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
@Table (name = "custom_attributes_keys")
public class CustomAttributesKeys implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="custom_attributes_id")
    private Integer customAttributesId;

    @Column (name="key_name")
    private String keyName;

    @Column (name="kay_label")
    private String kayLabel;

    @ManyToOne
    @Column (name="status_id")
    private Status status;

}