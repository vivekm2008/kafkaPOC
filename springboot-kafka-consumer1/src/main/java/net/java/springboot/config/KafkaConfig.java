package net.java.springboot.config;

import net.java.springboot.payload.User;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, User> userConsumerFactory() {
        JsonDeserializer<User> deserializer = new JsonDeserializer<>(User.class);
        deserializer.addTrustedPackages("*");
        deserializer.setRemoveTypeHeaders(false); // keep type headers if present
        deserializer.setUseTypeMapperForKey(false);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, User> userKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, User> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userConsumerFactory());

        // Optional but recommended: Error handler that logs and skips bad records
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                (record, ex) -> {
                    // log and move on (you can save the record to dead-letter store here)
                    LoggerFactory.getLogger(KafkaConfig.class)
                            .error("Skipping bad record. topic={}, partition={}, offset={}, exception={}",
                                    record.topic(), record.partition(), record.offset(), ex.getMessage());
                },
                new FixedBackOff(0L, 1L) // 1 attempt then skip
        );
        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }
}

