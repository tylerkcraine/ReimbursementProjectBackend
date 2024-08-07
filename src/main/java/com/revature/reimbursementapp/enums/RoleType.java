package com.revature.reimbursementapp.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.revature.reimbursementapp.models.Role;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum RoleType {
    ADMIN,
    MANAGER,
    USER;

    private Role role;

    public Role getRoleObject() {
        return role;
    }

    public void setRoleObject(Role role) {
        this.role = role;
    }
}
