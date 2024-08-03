package com.revature.reimbursementapp.Runner;

import com.revature.reimbursementapp.DAO.AccountDAO;
import com.revature.reimbursementapp.DAO.RoleDAO;
import com.revature.reimbursementapp.Enum.RoleType;
import com.revature.reimbursementapp.Model.Account;
import com.revature.reimbursementapp.Model.Role;
import com.revature.reimbursementapp.Service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBInitRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DBInitRunner.class);
    private final AccountDAO accountDAO;
    private final RoleDAO roleDAO;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DBInitRunner(AccountDAO ad, RoleDAO rd, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.accountDAO = ad;
        this.roleDAO = rd;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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

        Role adminRole = roleDAO.findRoleByRoleType(RoleType.ADMIN);
        List<Account> accountList = accountDAO.findAllByRole(adminRole);
        if (accountList.isEmpty()) {
            log.warn("Adding default admin account (root). The password should be changed in production!");
        }

        Role role = roleDAO.findRoleByRoleType(RoleType.ADMIN);
        Account newAccount = new Account(role, "admin", "Admin", "McAdmin", passwordEncoder.encode("password"));
        accountDAO.save(newAccount);

        Account account = accountDAO.findByUsername(newAccount.getUsername());
        String s = jwtService.generateJwtToken(account);
        System.out.println(s);

        String jwt = jwtService.parseJwtToken(s);
        System.out.println(jwt);
    }
}
