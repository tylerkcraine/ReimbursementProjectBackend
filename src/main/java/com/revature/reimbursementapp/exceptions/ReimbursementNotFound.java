package com.revature.reimbursementapp.exceptions;

public class ReimbursementNotFound extends RuntimeException {
    public ReimbursementNotFound() {
    }

    public ReimbursementNotFound(String message) {
        super(message);
    }

    public ReimbursementNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
