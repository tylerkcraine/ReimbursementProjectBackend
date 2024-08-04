package com.revature.reimbursementapp.dtos;

public class RegistrationDTO {
    private String username;
    private String rawPassword;
    private String firstName;
    private String lastName;

    public RegistrationDTO(String username, String rawPassword, String firstName, String lastName) {
        this.username = username;
        this.rawPassword = rawPassword;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public RegistrationDTO() {
    }

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
