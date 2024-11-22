package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@ResponseStatus(HttpStatus.OK)
public class SocialMediaController {
    AccountService accService;
    MessageService mesService;
    AccountRepository accRepo;
    MessageRepository mesRepo;

    public SocialMediaController(){
        this.accService = new AccountService(accRepo);
        //this.mesService = new MessageService(mesRepo);
    }

    //TODO: Fix Status Code
    @PostMapping(value = "/register")
    public ResponseEntity newAccount(@RequestBody String username, @RequestBody String password) {
        System.out.println("p1");
        Account foundAcc = accService.findAccountByUsername(username);
        
            if(username == "" || password == "" || password.length() < 4){
                System.out.println("f1");
                return ResponseEntity.status(400).body("Bad Request");
            }
            else if(foundAcc != null){
                System.out.println("f2");
                return ResponseEntity.status(409).body("Username alreasy exists");
            }
            else{
                System.out.println("p2");
                Account newAcc = accService.registerAccount(new Account(username, password));
                return ResponseEntity.status(200).body(newAcc); //TODO
            }
       
    }

    //TODO: Fix Status Code
    @PostMapping("/login")
    public ResponseEntity AccountLogin(@RequestBody String username, @RequestBody String password){
        Account foundAcc = accService.findAccountByUsername(username);
        if(username == "" || password == "" || password.length() < 4){
            return ResponseEntity.status(401).body("Unauthorized");
        }
        else {  
            if(foundAcc != null){
                return ResponseEntity.status(401).body("Unauthorized");
            }
            else {
                return ResponseEntity.status(200).body(foundAcc);
            }
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
