package net.java.springboot.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic-json.name}")
    private String topicJsonName;


    @Bean
    public NewTopic javaguidesJsonTopic(){
        return TopicBuilder.name(topicJsonName)
                .build();
    }


    @Value("${spring.kafka.topic-single-consumer.name}")
    private String topicJsonNameSingle;


    @Bean
    public NewTopic javaguidesJsonTopicSingle(){
        return TopicBuilder.name(topicJsonNameSingle)
                .build();
    }
}
