package com.ad.services.exception;

public class AdServicesException extends Exception {

  private static final long serialVersionUID = 1075552797371019804L;

  public AdServicesException() {
    super();
  }

  public AdServicesException(final String message) {
    super(message);
  }

  public AdServicesException(final Throwable t) {
    super(t);
  }

  public AdServicesException(final String message, final Throwable t) {
    super(message, t);
  }

}
