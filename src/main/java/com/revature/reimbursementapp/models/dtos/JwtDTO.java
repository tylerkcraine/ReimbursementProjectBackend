package com.revature.reimbursementapp.models.dtos;

import com.revature.reimbursementapp.enums.RoleType;
import com.revature.reimbursementapp.models.Account;
import com.revature.reimbursementapp.models.Role;

public class JwtDTO {
    private Integer accountId;
    private String username;
    private RoleType roleType;

    public JwtDTO(Integer accountId, String username, RoleType roleType) {
        this.accountId = accountId;
        this.username = username;
        this.roleType = roleType;
    }

    public JwtDTO() {
    }

    public JwtDTO(Account account) {
        this.accountId = account.getAccountId();
        this.roleType = account.getRole().getRoleType();
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

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRole(RoleType roleType) {
        this.roleType = roleType;
    }
}
