package com.hessel.desafio_anota_ai;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class DesafioAnotaAiApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().load();
        System.setProperty("aws.accessKeyId", dotenv.get("AWS_ACCESS_KEY_ID"));
        System.setProperty("aws.secretKey", dotenv.get("AWS_SECRET_ACCESS_KEY"));
        System.setProperty("aws.region", dotenv.get("AWS_REGION"));
        System.setProperty("aws.sns.topic.catalog.arn", dotenv.get("AWS_SNS_TOPIC_CATALOG_ARN"));

		SpringApplication.run(DesafioAnotaAiApplication.class, args);
	}

}
