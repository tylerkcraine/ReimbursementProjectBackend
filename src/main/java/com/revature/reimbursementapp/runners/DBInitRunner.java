package com.revature.reimbursementapp.runners;

import com.revature.reimbursementapp.daos.AccountDAO;
import com.revature.reimbursementapp.daos.RoleDAO;
import com.revature.reimbursementapp.dtos.LoginRequestDTO;
import com.revature.reimbursementapp.enums.RoleType;
import com.revature.reimbursementapp.models.Account;
import com.revature.reimbursementapp.models.Role;
import com.revature.reimbursementapp.services.JwtService;
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
            rt.setRoleObject(roleDAO.findRoleByRoleType(rt));
        }

        List<Account> accountList = accountDAO.findAllByRole(RoleType.ADMIN.getRoleObject());
        if (accountList.isEmpty()) {
            log.warn("Adding default admin account (admin). The password should be changed in production!");
        }

        Account newAccount = new Account(RoleType.ADMIN.getRoleObject(), "admin", "Admin", "McAdmin", passwordEncoder.encode("password"));
        accountDAO.save(newAccount);

        String s = jwtService.generateJwtToken(new LoginRequestDTO("admin","password"));
        System.out.println(s);

        String jwt = jwtService.parseJwtToken(s);
        System.out.println(jwt);
    }
}
