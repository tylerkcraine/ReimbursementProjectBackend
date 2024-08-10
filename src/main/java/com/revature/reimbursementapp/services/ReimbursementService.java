package com.revature.reimbursementapp.services;

import com.revature.reimbursementapp.daos.AccountDAO;
import com.revature.reimbursementapp.daos.ReimbursementDAO;
import com.revature.reimbursementapp.enums.RoleType;
import com.revature.reimbursementapp.enums.Status;
import com.revature.reimbursementapp.exceptions.AccountNotFoundException;
import com.revature.reimbursementapp.exceptions.ReimbursementNotFound;
import com.revature.reimbursementapp.exceptions.UnauthorizedException;
import com.revature.reimbursementapp.models.Account;
import com.revature.reimbursementapp.models.Reimbursement;
import com.revature.reimbursementapp.models.dtos.JwtDTO;
import com.revature.reimbursementapp.models.dtos.ReimbursementDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
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

    public void saveReimbursement(ReimbursementDTO reimbursement, JwtDTO jwt) {
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

    public void updateReimbursement(ReimbursementDTO reimbursementDTO, JwtDTO jwt) {
        if (jwt.getRoleType() == RoleType.USER && reimbursementDTO.getStatus() != null) {
            throw new UnauthorizedException("Users can't change the status of reimbursements");
        }

        Optional<Reimbursement> possibleReimbursement = reimbursementDAO.findById(reimbursementDTO.getReimbursementId());
        Reimbursement reimbursement = possibleReimbursement.orElseThrow(ReimbursementNotFound::new);

        if (reimbursementDTO.getAccountId() != null) {
            Optional<Account> possibleAccount = accountDAO.findById(reimbursementDTO.getAccountId());
            Account account = possibleAccount.orElseThrow(AccountNotFoundException::new);
            reimbursement.setAccount(account);
        } if (reimbursementDTO.getDescription() != null) {
            reimbursement.setDescription(reimbursementDTO.getDescription());
        } if (reimbursementDTO.getAmount() != null) {
            reimbursement.setAmount(reimbursementDTO.getAmount());
        } if (reimbursementDTO.getStatus() != null) {
            reimbursement.setStatus(reimbursementDTO.getStatus());
        }
        reimbursementDAO.flush();
    }
}
