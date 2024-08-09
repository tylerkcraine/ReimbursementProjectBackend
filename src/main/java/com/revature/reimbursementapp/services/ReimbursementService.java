package com.revature.reimbursementapp.services;

import com.revature.reimbursementapp.daos.ReimbursementDAO;
import com.revature.reimbursementapp.enums.RoleType;
import com.revature.reimbursementapp.exceptions.UnauthorizedException;
import com.revature.reimbursementapp.models.Reimbursement;
import com.revature.reimbursementapp.models.dtos.JwtDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReimbursementService {
    private final JwtService jwtService;
    private final ReimbursementDAO reimbursementDAO;

    @Autowired
    public ReimbursementService(JwtService jwtService, ReimbursementDAO reimbursementDAO) {
        this.jwtService = jwtService;
        this.reimbursementDAO = reimbursementDAO;
    }

    public void saveReimbursement(Reimbursement reimbursement, JwtDTO jwt) {
        if (jwt.getRoleType() == RoleType.USER &&
                reimbursement.getAccount().getAccountId() != jwt.getAccountId()) {
                throw new UnauthorizedException("User can't make reimbursement");
        }
    }
}
