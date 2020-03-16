package com.example.service.service;

import com.example.service.domain.RequestCouponMessage;
import com.example.service.sqs.SqsPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    SqsPublisher sqsPublisher;

    public Long decrQntAndPublishToSQS(String eventId, String productId) {

        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        HashOperations<String,String,Integer> hashOperations = redisTemplate.opsForHash();

        Long quantity = hashOperations.increment(productId + ":" + eventId, "quantity", -1);

        if(quantity > 0) {

            sqsPublisher.send(RequestCouponMessage.builder()
                    .eventId(eventId)
                    .productId(productId)
                    .build());
        }

        return quantity;
    }

}
