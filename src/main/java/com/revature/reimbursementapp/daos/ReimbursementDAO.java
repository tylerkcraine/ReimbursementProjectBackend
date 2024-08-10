package com.revature.reimbursementapp.daos;

import com.revature.reimbursementapp.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReimbursementDAO extends JpaRepository<Reimbursement, Integer> {
    List<Reimbursement> findAllByAccount_AccountId(Integer id);
}
