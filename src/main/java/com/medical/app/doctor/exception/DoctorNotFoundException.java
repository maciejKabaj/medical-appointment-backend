package com.medical.app.doctor.exception;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(Long id) {
        super("id: " + id + " not found");
    }
}
