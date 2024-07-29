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

    @Column(unique = true)
    private String username;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    private List<Reimbursement> reimbursementList;

    public Account(Integer accountId, String firstName, String lastName, String username, Role role, List<Reimbursement> reimbursementList) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.role = role;
        this.reimbursementList = reimbursementList;
    }

    public Account(Role role, String username, String lastName, String firstName) {
        this.role = role;
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Account() {
    }
}
