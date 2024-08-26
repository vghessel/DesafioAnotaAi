package com.hessel.desafioanotaai.service.aws;

import com.amazonaws.services.sns.AmazonSNS;
import org.springframework.stereotype.Service;

@Service
public class AwsSnsService {
    AmazonSNS snsClient;
    Topic catalogTopic;

    public AwsSnsService(AmazonSNS snsClient, @Qualifier("catalogEventsTopic") Topic catalogTopic){
        this.snsClient = snsClient;
        this.catalogTopic = catalogTopic;
    }

    public void publish(MessageDTO message) {
        this.snsClient.publish(catalogTopic.getTopicArn(), message.toString());
    }
}