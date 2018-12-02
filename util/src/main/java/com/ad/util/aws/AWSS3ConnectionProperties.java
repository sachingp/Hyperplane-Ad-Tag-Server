package com.ad.util.aws;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AWSS3ConnectionProperties {

  private String region;

  private Integer connectionTimeOut = 10000;

  private Integer socketTimeOut = 25000;

  private Integer maxConnection = 50;

  private Long connectionMaxIdleMillis = 60000L;

  private int requestTimeout = 0;

  private int clientExecutionTimeout = 0;

  private long connectionTTL = -1;

  private Integer validateTimeIdleConnection = 5000;

  private Integer maxRetryOnFailure = 2;

}
