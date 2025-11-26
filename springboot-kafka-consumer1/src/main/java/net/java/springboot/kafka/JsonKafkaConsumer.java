package net.java.springboot.kafka;

import net.java.springboot.payload.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaConsumer.class);

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @KafkaListener(topics = "${spring.kafka.topic-json.name}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "userKafkaListenerFactory")
    public void consume(User user){
        LOGGER.info("Json message recieved Group1 -> {}", user.toString());
    }

}