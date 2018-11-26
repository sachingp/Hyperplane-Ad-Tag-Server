package com.ad.server.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseResponse<T> {

  private List<ErrorElement> errors;
  private T data;

  /***
   * <p> Add error to the list of errors. </p>
   *
   * @param error error that needs to be added.
   */
  public void addError(final ErrorElement error) {
    if (errors == null) {
      errors = new ArrayList<>();
    }
    errors.add(error);
  }
}
