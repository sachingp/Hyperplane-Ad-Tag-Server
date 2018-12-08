package com.ad.util.reflect;
public class ReflectionException extends Exception {

  private static final long serialVersionUID = -6338755860046602021L;

  public ReflectionException(final String message) {
    super(message);
  }

  public ReflectionException(final String message, final Throwable t) {
    super(message, t);
  }

  public ReflectionException(final Throwable t) {
    super(t);
  }

  public ReflectionException() {
    super();
  }

}