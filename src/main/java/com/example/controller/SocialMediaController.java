package com.example.controller;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    AccountRepository accRepo;
    MessageRepository mesRepo;

    public SocialMediaController(){
        this.accService = new AccountService(accRepo);
        this.mesService = new MessageService(mesRepo);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidInput(HttpMessageNotReadableException e){
        return ResponseEntity.badRequest().body("Invalid payload");
    }

    //TODO: Fix Status Code
    @PostMapping(value = "/register")
    public ResponseEntity newAccount(@RequestBody String username, @RequestBody String password) throws JsonProcessingException {
        /*try {
            if(username == "" || password == "" || password.length() < 4){
                return ResponseEntity.status(400).body("Bad Request");
            } 

            Account foundAcc = accService.findAccountByUsername(username);
            if(foundAcc != null){
                return ResponseEntity.status(409).body("Username already exists");
            } else {
                Account newAcc = accService.registerAccount(new Account(username, password));
                return ResponseEntity.status(200).body(newAcc);
            }
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(400).body(e);
        }*/
        return ResponseEntity.status(200).body(username);
        
       
    }

    //TODO: Fix Status Code
    @PostMapping("/login")
    public ResponseEntity AccountLogin(@RequestBody String username, @RequestBody String password){
        Account foundAcc = accService.findAccountByUsername(username);
        if(username == "" || password == "" || password.length() < 4 || foundAcc == null){
            return ResponseEntity.status(401).body("Unauthorized");
        } else {  
            return ResponseEntity.status(200).body(foundAcc);
        }
    }
    /*

    //TODO: Add logic to method
    @PostMapping("/messages")
    public Message newMessage(@RequestBody String message, @RequestBody String postedBy, @RequestBody Long timePosted){

    }

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
