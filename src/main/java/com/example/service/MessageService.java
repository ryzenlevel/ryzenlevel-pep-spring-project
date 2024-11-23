package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    MessageRepository mesRepo;

    @Autowired
    public MessageService(MessageRepository messageRepo){
        this.mesRepo = messageRepo;
    }

    public Message saveMessage(Message mes){
        return mesRepo.save(mes);
    }
    
}
