package com.practice.backend.config;

import com.practice.backend.front.controller.pojo.PaymentParams;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@Configuration
public class KafkaConfig {

    private String groupId = "group-id";

    private String replyTopic = "response";

    @Bean
    public ReplyingKafkaTemplate<String, PaymentParams, String> replyingKafkaTemplate(ProducerFactory<String, PaymentParams> pf,
                                                                                      ConcurrentKafkaListenerContainerFactory<String, String> factory) {
        ConcurrentMessageListenerContainer<String, String> replyContainer = factory.createContainer(replyTopic);
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId(groupId);
        return new ReplyingKafkaTemplate<>(pf, replyContainer);
    }

    @Bean
    public KafkaTemplate<String, String> replyTemplate(ProducerFactory<String, String> pf,
                                                       ConcurrentKafkaListenerContainerFactory<String, String> factory) {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(pf);
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setReplyTemplate(kafkaTemplate);
        return kafkaTemplate;
    }
}
