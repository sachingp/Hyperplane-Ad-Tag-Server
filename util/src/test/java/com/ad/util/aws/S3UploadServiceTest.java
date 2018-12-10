package com.ad.util.aws;

import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class S3UploadServiceTest {

  @Test
  public void testUploadFile() throws Exception {
    System.setProperty("aws.accessKeyId", "AKIAIFOHSXROOQCYPDQQ");
    System.setProperty("aws.secretKey", "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");
    final AWSS3Config config = new AWSS3Config();
    final S3UploadService service = new S3UploadService(
        config.amazonS3Client(new SystemPropertiesCredentialsProvider()));
    service.uploadFile("src/test/resources/test.upload", "ec2-18-191-183-129", "compute", "test");
  }

}
