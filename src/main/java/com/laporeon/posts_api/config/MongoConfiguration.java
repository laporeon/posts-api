package com.laporeon.posts_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.laporeon.posts_api.repositories")
@EnableMongoAuditing
public class MongoConfiguration {
}
