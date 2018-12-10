package com.ad.util.aws;

import com.ad.util.PropertiesUtil;
import com.ad.util.aws.exception.S3UploadException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.retry.RetryPolicy;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AWSS3Config {

  private final ObjectMapper mapper;

  public AWSS3Config() {
    mapper = new ObjectMapper();
  }

  public AmazonS3 amazonS3Client(final AWSCredentialsProvider credentials)
      throws S3UploadException {
    try {
      final AWSS3ConnectionProperties prop = getAWSProperties();
      AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard().withRegion(prop.getRegion());
      if (credentials == null) {
        builder = builder.withCredentials(credentials);
      }
      final AmazonS3 s3Client = builder.withClientConfiguration(clientConfiguartion(prop)).build();
      return s3Client;
    } catch (Exception ex) {
      log.error("Bean creation Exception: S3Client with exception:{}", ex);
      throw new S3UploadException(ex);
    }
  }

  private ClientConfiguration clientConfiguartion(AWSS3ConnectionProperties prop) {
    ClientConfiguration conf = new ClientConfiguration();
    conf.setConnectionTimeout(prop.getConnectionTimeOut());
    conf.setSocketTimeout(prop.getSocketTimeOut());
    conf.setMaxConnections(prop.getMaxConnection());
    conf.setConnectionMaxIdleMillis(prop.getConnectionMaxIdleMillis());
    conf.setRequestTimeout(prop.getRequestTimeout());
    conf.setClientExecutionTimeout(prop.getClientExecutionTimeout());
    conf.setConnectionTTL(prop.getConnectionTTL());
    conf.setValidateAfterInactivityMillis(prop.getValidateTimeIdleConnection());
    conf.setRetryPolicy(retrypolicy(prop.getMaxRetryOnFailure()));
    return conf;
  }

  private RetryPolicy retrypolicy(int maxTry) {
    return new RetryPolicy(PredefinedRetryPolicies.DEFAULT_RETRY_CONDITION,
        PredefinedRetryPolicies.DEFAULT_BACKOFF_STRATEGY, maxTry, false);
  }

  private AWSS3ConnectionProperties getAWSProperties() throws Exception {
    final AWSS3ConnectionProperties conf = mapper
        .readValue(PropertiesUtil.getProperty("aws.conf.json"), AWSS3ConnectionProperties.class);
    if (null == conf) {
      throw new Exception("AWS Property not set");
    }
    return conf;
  }

}
