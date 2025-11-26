package net.java.springboot.kafka;

import net.java.springboot.payload.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaSingleConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaSingleConsumer.class);
    @Value("${spring.kafka.consumer.group-id1}")
    private String groupId;

    @KafkaListener(topics = "${spring.kafka.topic-single-consumer.name}",
            groupId = "${spring.kafka.consumer.group-id1}",
            containerFactory = "userKafkaListenerFactory")
    public void consume(User user){
        LOGGER.info("Single Json message recieved Group1 -> {}", user.toString());
    }

}
