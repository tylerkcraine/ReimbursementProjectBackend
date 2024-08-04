package com.revature.reimbursementapp.daos;

import com.revature.reimbursementapp.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReimbursementDAO extends JpaRepository<Reimbursement, Integer> {
}
