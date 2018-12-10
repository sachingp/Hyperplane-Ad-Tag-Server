package com.ad.server.enums;

import lombok.Getter;

public enum ErrorMessage {

  //Generic enums
  GENERIC_SERVICE_FAILURE(ErrorCategories.GENERIC_SERVICE_FAILURE, "Service Failure:");

  @Getter
  ErrorCategories errorCategory;

  @Getter
  String errorMsg;

  ErrorMessage(ErrorCategories erroCategory, String errorMsg) {
    this.errorCategory = erroCategory;
    this.errorMsg = errorMsg;
  }
}	
  
