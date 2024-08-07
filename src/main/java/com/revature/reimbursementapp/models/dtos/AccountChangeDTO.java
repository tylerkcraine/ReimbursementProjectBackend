package com.revature.reimbursementapp.models.dtos;

import com.revature.reimbursementapp.enums.RoleType;

public class AccountChangeDTO {
    private Integer accountId;
    private String username;
    private String rawPassword;
    private RoleType roleType;

    public AccountChangeDTO(Integer accountId, String username, String rawPassword, RoleType roleType) {
        this.accountId = accountId;
        this.username = username;
        this.rawPassword = rawPassword;
        this.roleType = roleType;
    }

    public AccountChangeDTO() {
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

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
}
