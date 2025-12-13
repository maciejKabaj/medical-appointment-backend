package com.medical.app.patient.exception;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(Long id) {
      super("id: " + id + " not found");
    }
}
