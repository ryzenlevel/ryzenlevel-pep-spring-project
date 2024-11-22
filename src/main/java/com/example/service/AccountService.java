package com.example.service;

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

    public Account findAccount(Account acc){
        return accRepo.getById(acc.getAccountId());
    }

    public Account findAccountById(Account acc){
        Account foundAcc = accRepo.findAccountByAccountId(acc.getAccountId());

        if(foundAcc != null){
            return foundAcc;
        }
        else{
            return null;
        }
    }

    public Account findAccountByUsername(String username){
        Account foundAcc = accRepo.findAccountByUsername(username);
        
        if(foundAcc != null){
            return foundAcc;
        }
        else{
            return null;
        }
    }
}
