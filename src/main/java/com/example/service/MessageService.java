package com.example.service;

import java.util.List;
import java.util.Optional;

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

    //TODO: Fix issue
    public Message saveMessage(Message mes){
        return mesRepo.save(mes);
    }
    
    public List<Message> findAllMessages(){
        return mesRepo.findAll();
    }

    public Message findMessageById(int mes){
        Optional<Message> foundMes = mesRepo.findById(mes);

        if(foundMes.isPresent()){
            return foundMes.get();
        }
        else {
            return null;
        }
    }

    public int deleteMessage(int messageId){
        Optional<Message> foundMessage = mesRepo.findById(messageId);
        if(foundMessage.isPresent()){
            mesRepo.deleteById(messageId);
            Optional<Message> deletedMessage = mesRepo.findById(messageId);
            if(!deletedMessage.isPresent()){
                return 1;
            }
            else{
                return 0;
            } 
        }
        else{
            return 0;
        }        
    }

    public int updateMessage(int messageId, String messageText){
        Optional<Message> updateMessage = mesRepo.findById(messageId);

        if(updateMessage.isPresent()){
            Message mes = updateMessage.get();
            mes.setMessageText(messageText);
            mesRepo.save(mes);
            return 1;
        }
        else{
            return 0;
        }
    }

    //TODO:Fix issues
    public List<Message> findMessagesByAccountId(int accountId){
        return mesRepo.findAllByPostedBy(accountId);
    }
}
