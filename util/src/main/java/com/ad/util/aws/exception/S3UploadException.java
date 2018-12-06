package com.ad.util.aws.exception;

public class S3UploadException extends Exception {

  private static final long serialVersionUID = 2367698839198751513L;

  public S3UploadException() {
    super();
  }

  public S3UploadException(final String message) {
    super(message);
  }

  public S3UploadException(final Throwable t) {
    super(t);
  }

  public S3UploadException(final String message, final Throwable t) {
    super(message, t);
  }

}
