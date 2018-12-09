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
@Table (name = "tag_type")
public class TagType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name="tag_type_id")  
    private Integer tagTypeId;

    @Column (name="tag_type")
    private String tagType;

}