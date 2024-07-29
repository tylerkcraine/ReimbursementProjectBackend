package com.revature.reimbursementapp.DAO;

import com.revature.reimbursementapp.Enum.RoleType;
import com.revature.reimbursementapp.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO extends JpaRepository<Role, Integer> {
    Role findRoleByRoleType(RoleType roleType);
}