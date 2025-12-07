package com.medical.app.doctor.mapper;

import com.medical.app.doctor.dto.CreateDoctorRequest;
import com.medical.app.doctor.dto.DoctorDto;
import com.medical.app.doctor.dto.UpdateDoctorRequest;
import com.medical.app.doctor.entity.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public DoctorDto toDto(final Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .specialization(doctor.getSpecialization())
                .email(doctor.getEmail())
                .phone(doctor.getPhone())
                .description(doctor.getDescription())
                .build();
    }

    public Doctor toEntity(final CreateDoctorRequest request) {
        Doctor doctor = new Doctor();
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhone());
        doctor.setPwzNumber(request.getPwzNumber());
        doctor.setDescription(request.getDescription());
        return doctor;
    }

    public Doctor updateEntity(Doctor doctor, UpdateDoctorRequest request) {
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhone());
        doctor.setDescription(request.getDescription());
        return doctor;
    }
}
