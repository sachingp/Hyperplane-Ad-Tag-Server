package com.ad.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "city")
public class City implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "city_id")
  private Integer cityId;

  @Column(name = "geoname_id")
  private Integer geonameId;

  @ManyToOne
  @JoinColumn(name = "state_id")
  private State state;

  @Column(name = "city_name")
  private String cityName;

  @Column(name = "city_code")
  private String cityCode;

  @Column(name = "postal_code")
  private String postalCode;

  @Column(name = "census_data_dma_id")
  private Integer censusDataDmaId;

}