package com.revature.reimbursementapp.DAO;

import com.revature.reimbursementapp.Model.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReimbursementDAO extends JpaRepository<Reimbursement, Integer> {
}
