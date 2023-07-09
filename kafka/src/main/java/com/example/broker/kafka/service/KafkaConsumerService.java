package com.example.broker.kafka.service;

import com.example.broker.kafka.model.PaymentParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final Logger logger =
            LoggerFactory.getLogger(KafkaConsumerService.class);


    @KafkaListener(topics = "paymentParams", groupId = "group-id")
    @SendTo("response")
    public String handle(PaymentParams paymentParams) {

        logger.info(String.format("Payment consumed: %s", paymentParams.getDescription()));
        //need some random
        return "yes";
    }


    @Bean
    public StringJsonMessageConverter jsonConverter() {
        return new StringJsonMessageConverter();
    }
}
