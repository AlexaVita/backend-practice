package com.practice.backend.front.controller;

import com.practice.backend.dao.mapper.ExampleMapper;
import com.practice.backend.dao.model.Example;
import com.practice.backend.dao.model.SectorSettingsMap;
import com.practice.backend.dao.service.SectorService;
import com.practice.backend.dao.service.SectorSettingsMapService;
import com.practice.backend.front.controller.pojo.PaymentParams;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
public class MainRestController {

    private String requestTopic = "paymentParams";

    @Autowired
    private ReplyingKafkaTemplate<String, PaymentParams, String> replyingKafkaTemplate;

    @Autowired
    ExampleMapper exampleMapper;

    @Autowired
    SectorService sectorService;

    @Autowired
    SectorSettingsMapService sectorSettingsMapService;

    Logger logger = LoggerFactory.getLogger(MainRestController.class);


    @GetMapping("/ping")
    public String ping(@RequestAttribute("uuid") UUID uuid) {
        return "pong";
    }


    @GetMapping("/numbers")
    public List<Example> getNumbers(@RequestAttribute("uuid") UUID uuid) {

        return exampleMapper.getAll();
    }


    @GetMapping("/broker2")
    public String broker2(@RequestAttribute("uuid") UUID uuid) throws ExecutionException, InterruptedException {

        PaymentParams paymentParams = new PaymentParams(2L, 2.0, "working!", 2L, "sd@gmail.com");
        ProducerRecord<String, PaymentParams> record = new ProducerRecord<>(requestTopic, null, paymentParams.getDescription(), paymentParams);
        RequestReplyFuture<String, PaymentParams, String> future = replyingKafkaTemplate.sendAndReceive(record);
        ConsumerRecord<String, String> response = future.get();
        return response.value();

    }


    @GetMapping("/tom")
    public String tom(@RequestAttribute("uuid") UUID uuid) {
        logger.info("tom");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "tom";
    }

    @GetMapping("/insert")
    public void addSector(@RequestParam(name = "name") String name) {
        // Просто тест, нужно переделать на RequestBody
        sectorSettingsMapService.insert(new SectorSettingsMap(4L, 2L, name, "4"));
    }

    @GetMapping("/update")
    public void updateSector() {
        sectorSettingsMapService.update(new SectorSettingsMap(4L, 2L, "GIGIGIGIGIGI", "4"));
    }

    @GetMapping("/delete")
    public void deleteSector() {
        sectorSettingsMapService.delete(2L);
    }
}
