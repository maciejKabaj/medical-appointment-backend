package com.medical.app.user.entity;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("Administrator"),
    DOCTOR("Doctor"),
    PATIENT("Patient");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
