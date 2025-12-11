package com.medical.app.doctor.entity;

import lombok.Getter;

@Getter
public enum Specialization {

    INTERNAL_MEDICINE("Internal Medicine"),
    PEDIATRICS("Pediatrics"),
    CARDIOLOGY("Cardiology"),
    DERMATOLOGY("Dermatology"),
    NEUROLOGY("Neurology"),
    ORTHOPEDICS("Orthopedics"),
    OPHTHALMOLOGY("Ophthalmology");

    final private String description;

    Specialization(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
