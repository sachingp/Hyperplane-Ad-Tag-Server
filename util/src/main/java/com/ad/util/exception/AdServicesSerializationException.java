package com.ad.util.exception;

public class AdServicesSerializationException extends Exception {

  private static final long serialVersionUID = 2367698839198751513L;

  public AdServicesSerializationException() {
    super();
  }

  public AdServicesSerializationException(final String message) {
    super(message);
  }

  public AdServicesSerializationException(final Throwable t) {
    super(t);
  }

  public AdServicesSerializationException(final String message, final Throwable t) {
    super(message, t);
  }

}
