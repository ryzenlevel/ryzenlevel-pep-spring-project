package com.example.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    
    AccountService accService;
    MessageService mesService;

    public SocialMediaController(AccountService accService, MessageService mesService){
        this.accService = accService;
        this.mesService = mesService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> newAccount(@RequestBody Account acc) throws JsonProcessingException {
        if(acc.getUsername() == "" ||  acc.getPassword().length() < 4){
            return ResponseEntity.status(400).build();
        } 
        else if(accService.findAccountByUsername(acc.getUsername()) != null){
            return ResponseEntity.status(409).build();
        } else {
            Account newAcc = accService.registerAccount(acc);
            return ResponseEntity.status(200).body(newAcc);
        }
              
       
    }

    @PostMapping("/login")
    public ResponseEntity<Account> AccountLogin(@RequestBody Account acc) throws JsonProcessingException{
        if(acc.getUsername() != "" && acc.getPassword().length() > 4){
            Account foundAcc = accService.findAccountByUsername(acc.getUsername());
            if(foundAcc != null && foundAcc.getPassword().equals(acc.getPassword())){
                return ResponseEntity.status(200).body(foundAcc);
            }
            else {
                return ResponseEntity.status(401).build();
            }
        } else {  
            return ResponseEntity.status(401).build();
        }
    }
    

    //TODO: Fix status code issue
    @PostMapping("/messages")
    public ResponseEntity<Message> newMessage(@RequestBody Message mes){
        Account acc = accService.findAccountById(mes.getPostedBy());
        if(acc != null){
            if(mes.getMessageText() != ""){
                if(mes.getMessageText().length() < 255){
                    if(mes.getPostedBy() == acc.getAccountId()){ //here
                        Message savedMes = mesService.saveMessage(mes);
                        return ResponseEntity.status(200).body(savedMes);
                    } else {
                        return ResponseEntity.status(400).build();
                    }
                } else {
                    return ResponseEntity.status(400).build();
                }
            } else {
                return ResponseEntity.status(400).build();
            }
        } else {
            return ResponseEntity.status(400).build();
        }      
    } 
    

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages() {
        return ResponseEntity.status(200).body(mesService.findAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId){
        Message mes = mesService.findMessageById(messageId);
        return ResponseEntity.status(200).body(mes);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId){
        int deleteSuccess = mesService.deleteMessage(messageId);

        if(deleteSuccess == 0){
            return ResponseEntity.status(200).build();
        }
        else{
            return ResponseEntity.status(200).body(deleteSuccess);
        }
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable int messageId, @RequestBody Message mes){
        Message foundMessage = mesService.findMessageById(messageId);
        if(foundMessage != null){
            if(mes.getMessageText().length() < 255 && mes.getMessageText() != ""){
                int updatedMessage = mesService.updateMessage(messageId, mes.getMessageText());
                if(updatedMessage == 1){
                   return ResponseEntity.status(200).body(updatedMessage); 
                } else {
                    return ResponseEntity.status(400).build();
                }
                
            } else {
                return ResponseEntity.status(400).build();
            }
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    //TODO:Fix issues
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable int accountId){
        List<Message> foundMessages = mesService.findMessagesByAccountId(accountId);
        if(foundMessages != null){
            return ResponseEntity.status(200).body(foundMessages); 
        }
        else {
            return ResponseEntity.status(200).build();
        }
       
    }

}
