package com.ad.server.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "creative_format")
public class CreativeFormat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="creative_format_id")
    @GeneratedValue(strategy = GenerationType.AUTO)    
    private Integer creativeFormatId;

    @Column (name="format")
    private String format;

}