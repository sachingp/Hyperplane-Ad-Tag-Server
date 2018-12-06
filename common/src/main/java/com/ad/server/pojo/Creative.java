package com.ad.server.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table (name = "creative")
public class Creative implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="creative_id")
    @GeneratedValue(strategy = GenerationType.AUTO)    
    private Integer creativeId;

    @ManyToOne
    @JoinColumn (name="campaign_id")
    private Campaign campaign;

    @Column (name="name")
    private String name;

    @ManyToOne
    @JoinColumn (name="target_id")
    private Target target;

    @ManyToOne
    @JoinColumn (name="creative_size_id")
    private CreativeSize creativeSize;

    @ManyToOne
    @JoinColumn (name="creative_format_id")
    private CreativeFormat creativeFormat;

    @ManyToOne
    @JoinColumn (name="status_id")
    private Status status;

}