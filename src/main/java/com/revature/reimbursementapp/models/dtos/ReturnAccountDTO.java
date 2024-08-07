package com.revature.reimbursementapp.models.dtos;

import com.revature.reimbursementapp.models.Account;
import com.revature.reimbursementapp.models.Role;

public class ReturnAccountDTO {
    private Integer accountId;
    private String username;
    private String firstName;
    private String lastName;
    private Role role;

    public ReturnAccountDTO(Integer accountId, String username, String firstName, String lastName, Role role) {
        this.accountId = accountId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public ReturnAccountDTO() {
    }

    public ReturnAccountDTO(Account account) {
        this.accountId = account.getAccountId();
        this.username = account.getUsername();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.role = account.getRole();
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
