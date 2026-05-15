package com.artur.vendas.infrastructure.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic salesTopic() {
        return TopicBuilder.name("vendas-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
