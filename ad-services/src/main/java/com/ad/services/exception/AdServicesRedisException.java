package com.ad.services.exception;

public class AdServicesRedisException extends Exception {

  private static final long serialVersionUID = 1075552797371019804L;

  public AdServicesRedisException() {
    super();
  }

  public AdServicesRedisException(final String message) {
    super(message);
  }

  public AdServicesRedisException(final Throwable t) {
    super(t);
  }

  public AdServicesRedisException(final String message, final Throwable t) {
    super(message, t);
  }

}
