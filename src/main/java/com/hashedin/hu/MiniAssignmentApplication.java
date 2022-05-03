package com.hashedin.hu;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.hashedin.hu")
//@ComponentScan({"com.hashedin.hu","com.hashedin.hu.config"})
//@EnableDynamoDBRepositories("com.hashedin.hu.repositories")
public class MiniAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniAssignmentApplication.class, args);
	}

}
