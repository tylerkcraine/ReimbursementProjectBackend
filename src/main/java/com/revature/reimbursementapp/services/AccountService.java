package com.revature.reimbursementapp.services;

import com.revature.reimbursementapp.daos.AccountDAO;
import com.revature.reimbursementapp.dtos.RegistrationDTO;
import com.revature.reimbursementapp.enums.RoleType;
import com.revature.reimbursementapp.exceptions.AccountExistsException;
import com.revature.reimbursementapp.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountDAO accountDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountDAO accountDAO, PasswordEncoder passwordEncoder) {
        this.accountDAO = accountDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public Account getAccountByUsername(String username) {
        return accountDAO.findByUsername(username);
    }

    public Account saveAccount(RegistrationDTO registrationRequest) throws AccountExistsException {
        if (accountDAO.existsByUsername(registrationRequest.getUsername())) {
            throw new AccountExistsException();
        } else {
            String hashedPass = passwordEncoder.encode(registrationRequest.getRawPassword());
            Account newAccount = new Account(RoleType.USER.getRoleObject(),
                    registrationRequest.getUsername(), registrationRequest.getFirstName(),
                    registrationRequest.getLastName(),
                    hashedPass);
            accountDAO.save(newAccount);
            return accountDAO.findByUsername(registrationRequest.getUsername());
        }
    }
}
