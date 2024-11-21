package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
public class SocialMediaController {
    AccountService accService;
    MessageService mesService;

    public SocialMediaController(){
        this.accService = new AccountService();
        this.mesService = new MessageService();
    }

    //TODO: Add logic to method
    @PostMapping("/register")
    public Account newAccount(@RequestBody String username, @RequestBody String password) {
        if(username == "" || password == ""){
            
        }
        else if()
    }

    //TODO: Add logic to method
    @PostMapping("/login")
    public Account AccountLogin(@RequestBody String username, @RequestBody String password){
        if(username == "" || password == ""){
            
        }
    }

    //TODO: Add logic to method
    @PostMapping("/messages")
    public Message newMessage(@RequestBody String message, @RequestBody String postedBy){

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

}
