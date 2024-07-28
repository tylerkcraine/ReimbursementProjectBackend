package com.revature.reimbursementapp.Model;

import com.revature.reimbursementapp.Enum.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;

@Entity
public class Reimbursement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reimbursementId;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 9, fraction = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
