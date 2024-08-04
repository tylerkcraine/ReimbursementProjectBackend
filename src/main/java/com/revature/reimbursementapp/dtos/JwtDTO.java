package com.revature.reimbursementapp.dtos;

import com.revature.reimbursementapp.models.Account;
import com.revature.reimbursementapp.models.Role;

public class JwtDTO {
    private Integer accountId;
    private String username;
    private Role role;

    public JwtDTO(Integer accountId, String username, Role role) {
        this.accountId = accountId;
        this.username = username;
        this.role = role;
    }

    public JwtDTO() {
    }

    public JwtDTO(Account account) {
        this.accountId = account.getAccountId();
        this.role = account.getRole();
        this.username = account.getUsername();
    }
    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
