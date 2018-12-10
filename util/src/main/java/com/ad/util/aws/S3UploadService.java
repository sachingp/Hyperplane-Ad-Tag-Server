package com.ad.util.aws;

import com.ad.util.aws.exception.S3UploadException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class S3UploadService {

  private final AmazonS3 client;

  public S3UploadService(final AmazonS3 client) {
    this.client = client;
  }

  public String uploadFile(final String filePath, final String name, final String bucket,
      final String uploadPath)
      throws S3UploadException {
    if ((filePath == null || filePath.trim().isEmpty())
        || (name == null || name.trim().isEmpty())
        || (bucket == null || bucket.trim().isEmpty())
        || (uploadPath == null || uploadPath.trim().isEmpty())) {
      throw new NullPointerException("Null value not allowed");
    }
    log.debug("Upload process request received for file name:{} and in bucket:{}", name, bucket);
    long startTime = System.currentTimeMillis();
    StringBuilder str = new StringBuilder(uploadPath);
    try {
      if (!client.doesObjectExist(bucket, name)) {
        log.info("Uploading file:{} to s3 bucket:{}", name, bucket);
        File file = new File(filePath);
        PutObjectResult result = client.putObject(bucket, name, file);
        final String etag;
        if ((etag = result.getETag()) == null || etag.trim().isEmpty()) {
          log.error("S3 upload failed to upload file:{}", name);
          throw new S3UploadException("S3 Upload failed");
        }
      } else {
        log.debug("Already uploaded, Skipping Upload for file :{}", name);
      }
    } catch (Exception ex) {
      // To catch any disk related failures
      log.error("S3 Upload exception:{}", ex);
      throw new S3UploadException(ex.getMessage());
    }
    str.append(name);
    long duration = System.currentTimeMillis() - startTime;
    log.info("Time taken for upload: {}", duration);
    return str.toString();
  }

}
