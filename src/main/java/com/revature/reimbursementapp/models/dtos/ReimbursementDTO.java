package com.revature.reimbursementapp.models.dtos;

import com.revature.reimbursementapp.enums.Status;

import java.math.BigDecimal;

public class ReimbursementDTO {
    private Integer reimbursementId;
    private String description;
    private BigDecimal amount;
    private Status status;
    private Integer accountId;

    public ReimbursementDTO(String description, BigDecimal amount, Status status, Integer accountId) {
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.accountId = accountId;
    }

    public ReimbursementDTO(Integer reimbursementId, String description, BigDecimal amount, Status status, Integer accountId) {
        this.reimbursementId = reimbursementId;
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.accountId = accountId;
    }

    public ReimbursementDTO() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getReimbursementId() {
        return reimbursementId;
    }

    public void setReimbursementId(Integer reimbursementId) {
        this.reimbursementId = reimbursementId;
    }
}
