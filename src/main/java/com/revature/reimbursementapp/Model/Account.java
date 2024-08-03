package com.revature.reimbursementapp.Model;

import jakarta.persistence.*;

import java.util.List;

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
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
}
