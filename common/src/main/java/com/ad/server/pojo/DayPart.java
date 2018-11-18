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
@Table (name = "day_part")
public class DayPart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="day_part_id")
    private Integer dayPartId;

    @Column (name="day_part")
    private String dayPart;

}