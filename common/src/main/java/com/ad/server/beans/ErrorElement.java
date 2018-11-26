package com.ad.server.beans;

import com.ad.server.enums.ErrorMessage;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorElement implements Cloneable {

  String code;
  String message;

  public ErrorElement(ErrorMessage errorEnum, String extraMessage) {
    this.code = errorEnum.name();
    this.message = errorEnum.getErrorMsg().concat(extraMessage);
  }

}
