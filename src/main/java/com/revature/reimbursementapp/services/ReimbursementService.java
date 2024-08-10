package com.revature.reimbursementapp.services;

import com.revature.reimbursementapp.daos.AccountDAO;
import com.revature.reimbursementapp.daos.ReimbursementDAO;
import com.revature.reimbursementapp.enums.RoleType;
import com.revature.reimbursementapp.exceptions.AccountNotFoundException;
import com.revature.reimbursementapp.exceptions.UnauthorizedException;
import com.revature.reimbursementapp.models.Account;
import com.revature.reimbursementapp.models.Reimbursement;
import com.revature.reimbursementapp.models.dtos.JwtDTO;
import com.revature.reimbursementapp.models.dtos.NewReimbursementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ReimbursementService {
    private final JwtService jwtService;
    private final ReimbursementDAO reimbursementDAO;
    private final AccountDAO accountDAO;

    @Autowired
    public ReimbursementService(JwtService jwtService, ReimbursementDAO reimbursementDAO, AccountDAO accountDAO) {
        this.jwtService = jwtService;
        this.reimbursementDAO = reimbursementDAO;
        this.accountDAO = accountDAO;
    }

    public void saveReimbursement(NewReimbursementDTO reimbursement, JwtDTO jwt) {
        Optional<Account> possibleAccount = accountDAO.findById(reimbursement.getAccountId());
        Account account = possibleAccount.orElseThrow(AccountNotFoundException::new);

        if (jwt.getRoleType() == RoleType.USER &&
                !Objects.equals(account.getAccountId(), jwt.getAccountId())) {
                throw new UnauthorizedException("User can't make reimbursement");
        }

        Reimbursement r = new Reimbursement(reimbursement.getDescription(),
                reimbursement.getAmount(),
                reimbursement.getStatus(),
                account);

        this.reimbursementDAO.save(r);
    }
}
