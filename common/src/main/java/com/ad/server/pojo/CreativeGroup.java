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
@Table (name = "creative_group")
public class CreativeGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="creative_group_id")
    private Integer creativeGroupId;

    @Id
    @Column (name="creative_group_name")
    private String creativeGroupName;

    @Column (name="status_id")
    private Integer statusId;

}