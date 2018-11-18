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
@Table (name = "status")
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="status_id")
    private Integer statusId;

    @Column (name="status_desc")
    private String statusDesc;

}