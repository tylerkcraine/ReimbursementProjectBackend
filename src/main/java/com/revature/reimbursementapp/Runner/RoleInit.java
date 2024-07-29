package com.revature.reimbursementapp.Runner;

import com.revature.reimbursementapp.DAO.RoleDAO;
import com.revature.reimbursementapp.Enum.RoleType;
import com.revature.reimbursementapp.Model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RoleInit implements CommandLineRunner {

    private final RoleDAO roleDAO;

    @Autowired
    public RoleInit(RoleDAO r) {
        this.roleDAO = r;
    }

    @Override
    public void run(String... args) throws Exception {
        for (RoleType rt : RoleType.values()) {
            Role role = roleDAO.findRoleByRoleType(rt);
            if (role == null) {
                Role newRole = new Role(rt);
                roleDAO.saveAndFlush(newRole);
            }
        }
    }
}
