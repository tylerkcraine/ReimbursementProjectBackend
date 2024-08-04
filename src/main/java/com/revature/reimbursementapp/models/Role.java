package com.revature.reimbursementapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.reimbursementapp.enums.RoleType;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roleId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    @JsonIgnore
    private List<Account> accountList;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private RoleType roleType;

    public Role(Integer roleId, List<Account> accountList, RoleType roleType) {
        this.roleId = roleId;
        this.accountList = accountList;
        this.roleType = roleType;
    }

    public Role(RoleType roleType) {
        this.roleType = roleType;
    }

    public Role() {
    }
}
