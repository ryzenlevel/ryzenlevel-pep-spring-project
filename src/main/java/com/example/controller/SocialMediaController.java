package com.example.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

//Controller for SpringBoot
@RestController
public class SocialMediaController {
    
    //initialize service classes
    AccountService accService;
    MessageService mesService;

    //default constructor
    public SocialMediaController(AccountService accService, MessageService mesService){
        //start services
        this.accService = accService;
        this.mesService = mesService;
    }

    //Post method is called on /register
    @PostMapping("/register")
    /*
     * @params Account username, password
     * @returns status codes and saved info in database
     */
    public ResponseEntity<Account> newAccount(@RequestBody Account acc){
        //if username is blank or password length is less than 4 characters, return status 400 (Bad Request)
        if(acc.getUsername() == "" ||  acc.getPassword().length() < 4){
            return ResponseEntity.status(400).build();
        }
        //if username already exists, return code 409 (Conflict)
        else if(accService.findAccountByUsername(acc.getUsername()) != null){
            return ResponseEntity.status(409).build();
        } 
        //Returns code 200 (OK) and prints json of new account
        else {
            Account newAcc = accService.registerAccount(acc);
            return ResponseEntity.status(200).body(newAcc);
        }
              
       
    }

    //Post method is called on /login
    @PostMapping("/login")
    /*
     * @params account username, password
     * @returns status codes and saved info in database
     */
    public ResponseEntity<Account> AccountLogin(@RequestBody Account acc){
        //if username is not blank and password length is greater than or equal to 4
        if(acc.getUsername() != "" && acc.getPassword().length() >= 4){
            //Search database for account
            Account foundAcc = accService.findAccountByUsername(acc.getUsername());
            //if account is found and password matches account from database
            if(foundAcc != null && foundAcc.getPassword().equals(acc.getPassword())){
                //return code 200 (OK) and print json of account
                return ResponseEntity.status(200).body(foundAcc);
            }
            else {
                //return code 401 (Unauthorized)
                return ResponseEntity.status(401).build();
            }
        } else {  
            //return code 401 (Unauthorized)
            return ResponseEntity.status(401).build();
        }
    }
    
    //Post method is called on /messages
    @PostMapping("/messages")
    /*
     * @params message postedBy, messageText, timePosted
     * @returns status codes and json of saved message
     */
    public ResponseEntity<Message> newMessage(@RequestBody Message mes){
        //look for user in account database
        Account acc = accService.findAccountById(mes.getPostedBy());
        //Account is found
        if(acc != null){
            //Message is not blank
            if(mes.getMessageText() != ""){
                //Message text length less than 255
                if(mes.getMessageText().length() < 255){
                    //persist message in database
                    Message savedMes = mesService.saveMessage(mes);
                    //Return code 200 (OK) and json of saved message
                    return ResponseEntity.status(200).body(savedMes);
                } 
                else {
                    //Return code 400 (Bad Request)
                    return ResponseEntity.status(400).build();
                }
            } 
            else {
                //Return code 400 (Bad Request)
                return ResponseEntity.status(400).build();
            }
        } 
        else {
            //Return code 400 (Bad Request)
            return ResponseEntity.status(400).build();
        }      
    } 
    
    //Get method called on /messages
    @GetMapping("/messages")
    /*
     * @params none
     * @returns all messages in database with code 200 (OK)
     */
    public ResponseEntity<List<Message>> getMessages() {
        return ResponseEntity.status(200).body(mesService.findAllMessages());
    }

    //Get method called on /messages/{messageId}
    @GetMapping("/messages/{messageId}")
    /*
     * @params messageId fropm URI
     * @returns found message/no message and code 200 (OK)
     */
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId){
        Message mes = mesService.findMessageById(messageId);
        return ResponseEntity.status(200).body(mes);
    }

    //Delete method called on /messages/{messageId}
    @DeleteMapping("/messages/{messageId}")
    /*
     * @params messageId from URI
     * @returns value of deleteSuccess and code 200 (OK)
     */
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId){
        //Calls deleteMessage from messegeService and is returned either 0 or 1
        int deleteSuccess = mesService.deleteMessage(messageId);

        //if no message was found
        if(deleteSuccess == 0){
            //return code 200 (OK) with no body
            return ResponseEntity.status(200).build();
        }
        else{
            //return code 200 (OK) and value of 1
            return ResponseEntity.status(200).body(deleteSuccess);
        }
    }

    //Update method called on /messages/{messageId}
    @PatchMapping("/messages/{messageId}")
    /*
     * @params messageId, message
     * @returns status codes and updated message from database
     */
    public ResponseEntity<Integer> updateMessageById(@PathVariable int messageId, @RequestBody Message mes){
        //check if message already exists
        Message foundMessage = mesService.findMessageById(messageId);
        //Message was found
        if(foundMessage != null){
            //new text length is less than 255 and not blank
            if(mes.getMessageText().length() < 255 && mes.getMessageText() != ""){
                //calls updateMessage in messageService
                int updatedMessage = mesService.updateMessage(messageId, mes.getMessageText());
                //message was updated
                if(updatedMessage == 1){
                    //return code 200 (OK) and print json of updated message
                    return ResponseEntity.status(200).body(updatedMessage);
                } 
                //message was not updated
                else {
                    //return code 400 (Bad Request)
                    return ResponseEntity.status(400).build();
                }  
            } 
            else {
                //return code 400 (Bad Request)
                return ResponseEntity.status(400).build();
            }
        } 
        else {
            //return code 400 (Bad Request)
            return ResponseEntity.status(400).build();
        }
    }

    //Get method called on /accounts/{accountId}/messages
    @GetMapping("/accounts/{accountId}/messages")
    /*
     * @params accountId from URI
     * @returns messages found with accountId
     */
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable int accountId){
        //calls service method to search database
        List<Message> foundMessages = mesService.findMessagesByAccountId(accountId);
        //messages were found
        if(foundMessages != null){
            //return code 200 (OK) and all messages with accountId
            return ResponseEntity.status(200).body(foundMessages); 
        }
        //messages not found
        else {
            //return code 200 (OK) and no body
            return ResponseEntity.status(200).build();
        }
    }

}
