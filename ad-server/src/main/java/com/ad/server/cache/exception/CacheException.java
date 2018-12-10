package com.ad.server.cache.exception;

public class CacheException extends Exception {

  private static final long serialVersionUID = 4177427421729465466L;

  public CacheException() {
    super();
  }

  public CacheException(final String message) {
    super(message);
  }

  public CacheException(final Throwable t) {
    super(t);
  }

  public CacheException(final String message, final Throwable t) {
    super(message, t);
  }

}
