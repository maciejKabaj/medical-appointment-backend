package com.medical.app.patient.mapper;

import com.medical.app.patient.dto.CreatePatientRequest;
import com.medical.app.patient.dto.PatientDto;
import com.medical.app.patient.dto.UpdatePatientRequest;
import com.medical.app.patient.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public PatientDto toDto(final Patient patient) {
        return PatientDto.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .phone(patient.getPhone())
                .build();
    }

    public Patient toEntity(final CreatePatientRequest request) {
        Patient patient = new Patient();
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setPhone(request.getPhone());
        patient.setPesel(request.getPesel());
        return patient;
    }

    public void updateEntity(Patient patient, UpdatePatientRequest request) {
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setPhone(request.getPhone());
    }
}
