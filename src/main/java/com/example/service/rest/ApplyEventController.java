package com.example.service.rest;

import com.example.service.service.EventService;
import com.example.service.sqs.SqsPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

@RestController
@Slf4j
public class ApplyEventController {

    @Autowired
    SqsPublisher sqsPublisher;


    @Autowired
    EventService eventService;


    @RequestMapping(
            path = "/event",
            method = RequestMethod.GET)
    public ResponseEntity<Long> applyEvent(@RequestParam String productId, @RequestParam String eventId) {

        log.info("productId=" + productId + "eventId=" + eventId);

        return new ResponseEntity<>(eventService.decrQntAndPublishToSQS(eventId, productId), HttpStatus.OK);
    }
}
