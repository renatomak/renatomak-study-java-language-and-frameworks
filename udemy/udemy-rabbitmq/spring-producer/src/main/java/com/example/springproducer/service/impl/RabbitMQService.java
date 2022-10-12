package com.example.springproducer.service.impl;

import com.example.springproducer.amqp.AmqpProducer;
import com.example.springproducer.dto.Message;
import com.example.springproducer.service.AmqpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService implements AmqpService {

    @Autowired
    private AmqpProducer<Message> ampq;

    @Override
    public void sendToConsumer(final Message message) {
        ampq.producer(message);
    }
}
