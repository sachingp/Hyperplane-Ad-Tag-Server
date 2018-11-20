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
@Table (name = "user_accounts")
public class UserAccounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="user_accounts_id")
    private Integer userAccountsId;

    @Column (name="user_id")
    private Integer userId;

    @ManyToOne
    @Column (name="account_id")
    private Account account;

    @ManyToOne
    @Column (name="user_role_id")
    private UserRoles userRole;

    @ManyToOne
    @Column (name="status_id")
    private Status status;

}