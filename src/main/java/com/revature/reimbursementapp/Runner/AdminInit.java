package com.revature.reimbursementapp.Runner;

import com.revature.reimbursementapp.DAO.AccountDAO;
import com.revature.reimbursementapp.DAO.RoleDAO;
import com.revature.reimbursementapp.Enum.RoleType;
import com.revature.reimbursementapp.Model.Account;
import com.revature.reimbursementapp.Model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminInit implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminInit.class);
    private final AccountDAO accountDAO;
    private final RoleDAO roleDAO;

    @Autowired
    public AdminInit(AccountDAO ad, RoleDAO rd) {
        this.accountDAO = ad;
        this.roleDAO = rd;
    }

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = roleDAO.findRoleByRoleType(RoleType.ADMIN);
        List<Account> accountList = accountDAO.findAllByRole(adminRole);
        if (accountList.isEmpty()) {
            log.warn("Adding default admin account (root). The password should be changed in production!");
        }
    }
}
