package com.revature.reimbursementapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.reimbursementapp.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

@Entity
public class Reimbursement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reimbursementId;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 9, fraction = 2)
    @Min(0)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Reimbursement(Integer reimbursementId, String description, BigDecimal amount, Status status, Account account) {
        this.reimbursementId = reimbursementId;
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.account = account;
    }

    public Reimbursement(String description, BigDecimal amount, Status status, Account account) {
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.account = account;
    }

    public Reimbursement() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public @DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 9, fraction = 2) @Min(0) BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 9, fraction = 2) @Min(0) BigDecimal amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getReimbursementId() {
        return reimbursementId;
    }

    public void setReimbursementId(Integer reimbursementId) {
        this.reimbursementId = reimbursementId;
    }
}
