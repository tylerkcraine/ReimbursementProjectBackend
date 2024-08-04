package com.revature.reimbursementapp.daos;

import com.revature.reimbursementapp.enums.RoleType;
import com.revature.reimbursementapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO extends JpaRepository<Role, Integer> {
    Role findRoleByRoleType(RoleType roleType);
}