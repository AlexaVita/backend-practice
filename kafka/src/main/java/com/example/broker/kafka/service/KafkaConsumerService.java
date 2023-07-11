package com.example.broker.kafka.service;

import com.example.broker.kafka.model.PaymentParams;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class KafkaConsumerService {

    private final Logger logger =
            LoggerFactory.getLogger(KafkaConsumerService.class);

    Random random = new Random();


    @KafkaListener(topics = "paymentParams", groupId = "group-id")
    @SendTo("response")
    public String handle(PaymentParams paymentParams) {

        logger.info(String.format("Payment consumed: %s", paymentParams.getDescription()));

        // some random
        String[] responses = {"APPROVED", "REJECTED", "FAILED", "TIMEOUT"};
        String response = responses[random.nextInt(responses.length)];

        logger.info(String.format("Response is: %s", response));

        if ("TIMEOUT".equals(response)) {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return response;
    }


    @Bean
    public StringJsonMessageConverter jsonConverter() {
        return new StringJsonMessageConverter();
    }
}
