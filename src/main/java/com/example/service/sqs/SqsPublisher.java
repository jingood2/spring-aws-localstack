package com.example.service.sqs;

import com.example.service.domain.EventMessage;
import com.example.service.domain.RequestCouponMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class SqsPublisher {

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${sqs.queue.event}")
    private String queue;

    public void send(EventMessage msg) {
        queueMessagingTemplate.convertAndSend(queue, msg);
    }

    public void send(RequestCouponMessage msg) {
        queueMessagingTemplate.convertAndSend(queue, msg);
    }

}
