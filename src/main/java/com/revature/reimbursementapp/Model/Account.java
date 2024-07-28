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
}
