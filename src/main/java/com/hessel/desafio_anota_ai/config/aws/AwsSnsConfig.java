package com.hessel.desafioanotaai.config.aws;

@Configuration
public class AwsSnsConfig {
    @Value("${aws.region}")
    private String region;
    @Value("${aws.accessKeyId}")
    private String accessKeyId;
    @Value("${aws.secretKey}")
    private String secretKey;
    @Value("${aws.sns.topic.catalog.arn}")
    private String catalogTopicArn;

    @Bean
    public AmazonSNS amazonSNSBuilder() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretKey);

        return AmazonSNSClientBuilder
        .standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion(region)
        .builder();
    }

    @Bean(name = "catalogEventsTopic")
    public Topic snsCatalogTopicBuilder() {
        return new Topic().withTopicArn(catalogTopicArn);
    }
}