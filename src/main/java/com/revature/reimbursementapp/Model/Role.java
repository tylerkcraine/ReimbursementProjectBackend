package com.revature.reimbursementapp.Model;

import com.revature.reimbursementapp.Enum.RoleType;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roleId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    private List<Account> accountList;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private RoleType roleType;
}
