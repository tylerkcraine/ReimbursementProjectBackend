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

    private void validateUser() {

    }

    public List<Account> getAllAccounts() {
        return accountDAO.findAll();
    }

    public Account getAccountById(int id) {
        return accountDAO.findById(id).orElseThrow(AccountNotFoundException::new);
    }

    public Account getAccountByUsername(String username) {
        return accountDAO.findByUsername(username).orElseThrow(AccountNotFoundException::new);
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

        if (requestingAccount.getRole().getRoleType() == RoleType.USER) {
            if (accountChangeDTO.getRoleType() != RoleType.USER) {
                throw new UnauthorizedException("Users can't change their own role to anything but USER");
            }
            if (!accountChangeDTO.getAccountId().equals(requestingAccount.getAccountId())) {
                throw new UnauthorizedException("Users can't change anyone's account other than their own");
            }
        }

        //unboxing account
        Optional<Account> changeAccount = accountDAO.findById(accountChangeDTO.getAccountId());
        Account account = changeAccount.orElseThrow(AccountNotFoundException::new);

        // when we only have one admin account, don't allow its role to change from admin
        if (accountDAO.countByRole(RoleType.ADMIN.getRoleObject()) == 1 &&
                accountChangeDTO.getRoleType() != RoleType.ADMIN &&
                account.getRole().getRoleType() == RoleType.ADMIN) {
            throw new SingleAdminException();
        }

        //setting new fields
        //checking to see if a new username is provided and if it is taken already by another user
        if (accountChangeDTO.getUsername() != null && !account.getUsername().equals(accountChangeDTO.getUsername())) {
            if (accountDAO.existsByUsername(accountChangeDTO.getUsername()))
                throw new AccountExistsException();
            account.setUsername(accountChangeDTO.getUsername());
        }
        account.setFirstName(accountChangeDTO.getFirstName());
        account.setLastName(accountChangeDTO.getLastName());
        if (accountChangeDTO.getRawPassword() != null) {
            String encodedPass = passwordEncoder.encode(accountChangeDTO.getRawPassword());
            account.setPassword(encodedPass);
        }
        account.setRole(accountChangeDTO.getRoleType().getRoleObject());
        accountDAO.flush();
    }

    public void removeAccount(Integer account_id, JwtDTO requestingUser) {
        if (requestingUser.getRoleType() == RoleType.USER && !requestingUser.getAccountId().equals(account_id)) {
            throw new UnauthorizedException("Users can only delete their own account.");
        }

        Optional<Account> possibleAccount = accountDAO.findById(account_id);
        Account account = possibleAccount.orElseThrow(AccountNotFoundException::new);

        //checking to see if we are trying to delete the last admin account
        if (account.getRole().getRoleType() == RoleType.ADMIN &&
                accountDAO.countByRole(RoleType.ADMIN.getRoleObject())==1) {
            throw new SingleAdminException();
        }

        accountDAO.delete(account);
    }
}
