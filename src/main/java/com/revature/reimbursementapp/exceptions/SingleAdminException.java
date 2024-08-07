package com.revature.reimbursementapp.exceptions;

public class SingleAdminException extends RuntimeException {
    public SingleAdminException() {
    }

    public SingleAdminException(String message) {
        super(message);
    }

    public SingleAdminException(String message, Throwable cause) {
        super(message, cause);
    }
}
