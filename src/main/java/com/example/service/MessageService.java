package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    //initialize repository
    MessageRepository mesRepo;

    //default constructor
    @Autowired
    public MessageService(MessageRepository messageRepo){
        this.mesRepo = messageRepo;
    }

    //saves data to database
    public Message saveMessage(Message mes){
        return mesRepo.save(mes);
    }
    
    //return all messages from database
    public List<Message> findAllMessages(){
        return mesRepo.findAll();
    }

    //find specific message with messageId
    public Message findMessageById(int mes){
        //finds message using messageId
        Optional<Message> foundMes = mesRepo.findById(mes);

        //if message is found, return message. Otherwise, return null
        if(foundMes.isPresent()){
            return foundMes.get();
        }
        else {
            return null;
        }
    }

    //Deletes message from database
    public int deleteMessage(int messageId){
        //search database for message
        Optional<Message> foundMessage = mesRepo.findById(messageId);
        //Message is found
        if(foundMessage.isPresent()){
            //delete message from database
            mesRepo.deleteById(messageId);
            //returns 1 to controller to indicate message is gone
            return 1; 
        }
        else{
            //return 0 if message is not found
            return 0;
        }        
    }

    //Updates message in database
    public int updateMessage(int messageId, String messageText){
        //search database for message
        Optional<Message> updateMessage = mesRepo.findById(messageId);

        //Message is found
        if(updateMessage.isPresent()){
            //get message for updates
            Message mes = updateMessage.get();
            //change messageText from old to new
            mes.setMessageText(messageText);
            //persist message in database with updated text
            mesRepo.save(mes);
            //return 1 to indicate update was successful
            return 1;
        }
        else{
            //if message was not found, return 0
            return 0;
        }
    }

    //returns all messages with specific postedBy accountId
    public List<Message> findMessagesByAccountId(int accountId){
        return mesRepo.findAllByPostedBy(accountId);
    }
}
