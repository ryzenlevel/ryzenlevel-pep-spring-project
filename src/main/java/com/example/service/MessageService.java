package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.repository.MessageRepository;

public class MessageService {
    MessageRepository mesRepo;

    @Autowired
    public MessageService(MessageRepository messageRepo){
        this.mesRepo = messageRepo;
    }
    
}
