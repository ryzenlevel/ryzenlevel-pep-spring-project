package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accRepo;
    
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accRepo = accountRepository;
    }

    public Account registerAccount(Account acc){
        return accRepo.save(acc);
    }

    public Account findAccountById(int acc){
        Optional<Account> foundAcc = accRepo.findById(acc);

        if(foundAcc.isPresent()){
            return foundAcc.get();
        }
        else{
            return null;
        }
    }

    public Account findAccountByUsername(String username){
        return accRepo.findByUsername(username);
    }
}
