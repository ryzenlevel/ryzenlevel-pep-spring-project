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
    @PostMapping("/register")
    public Account newAccount(@RequestBody String username, @RequestBody String password) {
        System.out.println("p1");
        Account foundAcc = accService.findAccountByUsername(username);
        try{
            if(username == "" || password == "" || password.length() < 4){
                System.out.println("f1");
                throw new Exception();
            }
            else if(foundAcc != null){
                System.out.println("f2");
                throw new Exception();
            }
            else{
                System.out.println("p2");
                return accService.registerAccount(new Account(username, password)); //TODO
            }
        } catch (Exception e){
            if(username == "" || password == "" || password.length() < 4){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username or password");
            }
            else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
            }
        }
    }

    //TODO: Fix Status Code
    @PostMapping("/login")
    public Account AccountLogin(@RequestBody String username, @RequestBody String password){
        try {
            Account foundAcc = accService.findAccountByUsername(username);
            if(username == "" || password == "" || password.length() < 4){
                throw new Exception();
            }
            else {  
                if(foundAcc != null){
                    throw new Exception();
                }
                else {
                    return foundAcc;
                }
            }
        } catch (Exception e) {
            // handle exception
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login failed");
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
