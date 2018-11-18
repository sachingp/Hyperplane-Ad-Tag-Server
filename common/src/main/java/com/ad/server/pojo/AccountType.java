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
@Table (name = "account_type")
public class AccountType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="account_type_id")
    private Integer accountTypeId;

    @Column (name="account_type_label")
    private String accountTypeLabel;

}