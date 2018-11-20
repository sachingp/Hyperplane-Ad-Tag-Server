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
@Table (name = "user_roles")
public class UserRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="user_roles_id")
    private Integer userRolesId;

    @ManyToOne
    @Column (name="user_id")
    private User user;

    @ManyToOne
    @Column (name="roles_id")
    private Roles roles;

    @ManyToOne
    @Column (name="status_id")
    private Status status;

}