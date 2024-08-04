package com.revature.reimbursementapp.services;

import com.revature.reimbursementapp.daos.AccountDAO;
import com.revature.reimbursementapp.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountDAO accountDAO;

    @Autowired
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account getAccountByUsername(String username) {
        return accountDAO.findByUsername(username);
    }
}
