package ibf2022.batch2.csf.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {

    @Value("${DO_STORAGE_KEY}")
    private String accessKey;
    @Value("${DO_STORAGE_SECRETKEY}")
    private String secretKey;
    @Value("${DO_STORAGE_ENDPOINT}")
    private String endpoint;
    @Value("${DO_STORAGE_ENDPOINT_REGION}")
    private String endpointRegion;
    
    @Bean
    public AmazonS3 createS3Client() {
        BasicAWSCredentials cred = new BasicAWSCredentials(accessKey, secretKey);
        EndpointConfiguration epConfig = new EndpointConfiguration(endpoint, endpointRegion);
        
        return AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(epConfig)
            .withCredentials(new AWSStaticCredentialsProvider(cred))
            .build();
    }
    
}
