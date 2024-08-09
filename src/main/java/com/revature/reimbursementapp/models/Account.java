package com.revature.reimbursementapp.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer accountId;

    private String firstName;

    private String lastName;

    private String password;

    @Column(unique = true)
    private String username;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountId, account.accountId) &&
                Objects.equals(firstName, account.firstName) &&
                Objects.equals(lastName, account.lastName) &&
                Objects.equals(password, account.password) &&
                Objects.equals(username, account.username) &&
                Objects.equals(role, account.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, firstName, lastName, password, username, role);
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.ALL)
    private List<Reimbursement> reimbursementList;

    public Account(Integer accountId, String firstName, String lastName, String username, Role role, List<Reimbursement> reimbursementList, String password) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.role = role;
        this.reimbursementList = reimbursementList;
        this.password = password;
    }

    public Account(Role role, String username, String firstName, String lastName, String password) {
        this.role = role;
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
    }

    public Account() {
    }

    public Integer getAccountId() {
        return accountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public List<Reimbursement> getReimbursementList() {
        return reimbursementList;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
