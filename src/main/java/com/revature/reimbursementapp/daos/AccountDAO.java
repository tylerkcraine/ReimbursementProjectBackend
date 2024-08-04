package com.revature.reimbursementapp.daos;

import com.revature.reimbursementapp.models.Account;
import com.revature.reimbursementapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountDAO extends JpaRepository<Account, Integer> {
    List<Account> findAllByRole(Role role);
    Account findByUsername(String username);
}
