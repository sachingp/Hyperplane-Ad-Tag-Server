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
@Table (name = "creative_group_coll")
public class CreativeGroupColl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="creative_group_coll_id")
    private Integer creativeGroupCollId;

    @ManyToOne
    @JoinColumn (name="creative_group_id")
    private CreativeGroup creativeGroup;

    @Column (name="creative_id")
    private Integer creativeId;

    @Column (name="distribution_percen")
    private Float distributionPercen;

    @ManyToOne
    @JoinColumn (name="status_id")
    private Status status;

}