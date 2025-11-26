package net.java.springboot.controller;

import net.java.springboot.kafka.JsonKafkaProducer_Single;
import net.java.springboot.payload.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/kafka/single")
public class JsonMessageSingleController {

    private JsonKafkaProducer_Single kafkaProducerSingle;

    public JsonMessageSingleController(JsonKafkaProducer_Single kafkaProducerSingle) {
        this.kafkaProducerSingle = kafkaProducerSingle;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody User user){
        kafkaProducerSingle.sendMessage(user);
        return ResponseEntity.ok("Json message sent to kafka topic single massage");
    }


}