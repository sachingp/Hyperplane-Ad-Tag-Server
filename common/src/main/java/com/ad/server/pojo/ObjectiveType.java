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
@Table (name = "objective_type")
public class ObjectiveType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="objective_type_id")
    private Integer objectiveTypeId;

    @Column (name="objective_name")
    private String objectiveName;

}