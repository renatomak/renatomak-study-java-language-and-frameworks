package com.example.spring.consumer.service.impl;

import com.example.spring.consumer.dto.Message;
import com.example.spring.consumer.service.ConsumerService;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Override
    public void action(final Message message) throws Exception{
//        throw new Exception("Erro");
        System.out.println(message.getText());
    }
}
