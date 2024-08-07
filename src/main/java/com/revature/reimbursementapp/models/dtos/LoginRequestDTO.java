package com.revature.reimbursementapp.models.dtos;

public class LoginRequestDTO {
    private String username;
    private String rawPassword;

    public LoginRequestDTO(String username, String rawPassword) {
        this.username = username;
        this.rawPassword = rawPassword;
    }

    public LoginRequestDTO() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }
}
