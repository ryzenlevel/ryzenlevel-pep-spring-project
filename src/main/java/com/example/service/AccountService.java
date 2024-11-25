package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    //initialize repository
    AccountRepository accRepo;
    
    //default constructor
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accRepo = accountRepository;
    }

    //persists data to database
    public Account registerAccount(Account acc){
        return accRepo.save(acc);
    }

    /*
     * @params accountId
     * @returns account or null
     */
    public Account findAccountById(int acc){
        //search database for id
        Optional<Account> foundAcc = accRepo.findById(acc);

        //if account is found, return account. Otherwise, return null
        if(foundAcc.isPresent()){
            return foundAcc.get();
        }
        else{
            return null;
        }
    }

    //search database for username
    public Account findAccountByUsername(String username){
        return accRepo.findByUsername(username);
    }

}
