package com.practice.backend.front.service;

import com.practice.backend.front.controller.pojo.PaymentParams;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.KafkaReplyTimeoutException;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class KafkaService {

    private final String requestTopic = "paymentParams";

    @Autowired
    private ReplyingKafkaTemplate<String, PaymentParams, String> replyingKafkaTemplate;

    public ConsumerRecord<String, String> sendMessageAndWaitForResponse(String description, PaymentParams paymentParams) throws ExecutionException, InterruptedException, KafkaReplyTimeoutException {
        //PaymentParams paymentParams = new PaymentParams(2L, 2.0, "working!", 2L, "sd@gmail.com");
        ProducerRecord<String, PaymentParams> record = new ProducerRecord<>(requestTopic, null, description, paymentParams);
        RequestReplyFuture<String, PaymentParams, String> future = replyingKafkaTemplate.sendAndReceive(record);

        // Ожидание ответа от consumer
        return future.get();
    }

}
