package com.revature.reimbursementapp.services;

import com.revature.reimbursementapp.daos.AccountDAO;
import com.revature.reimbursementapp.exceptions.*;
import com.revature.reimbursementapp.models.dtos.AccountChangeDTO;
import com.revature.reimbursementapp.models.dtos.JwtDTO;
import com.revature.reimbursementapp.models.dtos.RegistrationDTO;
import com.revature.reimbursementapp.enums.RoleType;
import com.revature.reimbursementapp.models.Account;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountService {

    private final AccountDAO accountDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountDAO accountDAO, PasswordEncoder passwordEncoder) {
        this.accountDAO = accountDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Account> getAllAccounts() {
        return accountDAO.findAll();
    }

    public Optional<Account> getAccountById(int id) { return accountDAO.findById(id); }

    public Optional<Account> getAccountByUsername(String username) {
        return accountDAO.findByUsername(username);
    }

    public void saveAccount(RegistrationDTO registrationRequest) throws AccountExistsException {
        if (accountDAO.existsByUsername(registrationRequest.getUsername())) {
            throw new AccountExistsException();
        } else {
            String hashedPass = passwordEncoder.encode(registrationRequest.getRawPassword());
            Account newAccount = new Account(RoleType.USER.getRoleObject(),
                    registrationRequest.getUsername(),
                    registrationRequest.getFirstName(),
                    registrationRequest.getLastName(),
                    hashedPass);
            accountDAO.save(newAccount);
        }
    }

    public void updateAccount(AccountChangeDTO accountChangeDTO, JwtDTO requestingUser) {
        Optional<Account> accountOptional = accountDAO.findById(requestingUser.getAccountId());
        Account requestingAccount = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (!passwordEncoder.matches(accountChangeDTO.getRawPassword(),requestingAccount.getPassword()) && requestingUser.getRoleType() != RoleType.ADMIN) {
            throw new PasswordIncorrectException();
        }

        // when we only have one admin account, don't allow its role to change from admin
        if (requestingAccount.getRole().getRoleType() == RoleType.ADMIN &&
                accountDAO.countByRole(RoleType.ADMIN.getRoleObject()) == 1 &&
                accountChangeDTO.getRoleType() != RoleType.ADMIN) {
            throw new SingleAdminException();
        }

        Optional<Account> changeAccount = accountDAO.findById(accountChangeDTO.getAccountId());
        Account account = changeAccount.orElseThrow(AccountNotFoundException::new);
        account.setUsername(accountChangeDTO.getUsername());
        account.setFirstName(accountChangeDTO.getFirstName());
        account.setLastName(accountChangeDTO.getLastName());
        if (accountChangeDTO.getRawPassword() == null) {
            
        }
        String encodedPass = passwordEncoder.encode(accountChangeDTO.getRawPassword());
        account.setPassword(encodedPass);
        account.setRole(accountChangeDTO.getRoleType().getRoleObject());
        accountDAO.flush();
    }

    public void removeAccount(AccountChangeDTO accountChangeDTO, JwtDTO requestingUser) {
    }
}
