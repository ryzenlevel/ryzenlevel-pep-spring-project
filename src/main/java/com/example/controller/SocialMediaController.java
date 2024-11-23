package com.example.controller;

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
    

    //TODO: Add logic to method
    @PostMapping("/messages")
    public ResponseEntity<Message> newMessage(@RequestBody Message mes){
        if(mes.getMessageText() != "" 
            && mes.getMessageText().length() < 255 
            && mes.getPostedBy().equals(accService.findAccountById(mes.getPostedBy()))){
            Message savedMes =  mesService.saveMessage(mes);
            return ResponseEntity.status(200).body(savedMes);
        } else {
            return ResponseEntity.status(400).build();
        }
    }
/*
    //TODO: Add logic to method
    @GetMapping("/messages")
    public Message getMessages() {

    }

    //TODO: Add logic to method
    @GetMapping("/messages/{messageId}")
    public Message getMessageById(@PathVariable long messageId){

    }

    //TODO: Add logic to method
    @DeleteMapping("/messages/{messageId}")
    public Message deleteMessageById(@PathVariable long messageId){

    }

    //TODO: Add logic to method
    @PatchMapping("/messages/{messageId}")
    public Message updateMessageById(@PathVariable long messageId){

    }
    */
}
