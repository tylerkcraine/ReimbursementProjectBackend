package com.revature.reimbursementapp.DAO;

import com.revature.reimbursementapp.Model.Account;
import com.revature.reimbursementapp.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountDAO extends JpaRepository<Account, Integer> {
    List<Account> findAllByRole(Role role);
}
