package com.medical.app.doctor.service;

import com.medical.app.doctor.dto.CreateDoctorRequest;
import com.medical.app.doctor.dto.DoctorDto;
import com.medical.app.doctor.dto.UpdateDoctorRequest;
import com.medical.app.doctor.entity.Doctor;
import com.medical.app.doctor.entity.Specialization;
import com.medical.app.doctor.exception.DoctorAlreadyExistsException;
import com.medical.app.doctor.exception.DoctorNotFoundException;
import com.medical.app.doctor.mapper.DoctorMapper;
import com.medical.app.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public DoctorDto createDoctor(CreateDoctorRequest request) {
        verifyUniqueness(request);
        Doctor doctor = doctorMapper.toEntity(request);
        Doctor saved = doctorRepository.save(doctor);
        return doctorMapper.toDto(saved);
    }

    public DoctorDto getDoctorById(Long id) {
        return doctorMapper.toDto(verifyDoctorById(id));
    }

    public List<DoctorDto> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toDto)
                .toList();
    }

    public List<DoctorDto> getDoctorsBySpecialization(Specialization specialization) {
        return doctorRepository.findBySpecialization(specialization).stream()
                .map(doctorMapper::toDto)
                .toList();
    }

    public DoctorDto updateDoctor(Long id, UpdateDoctorRequest request) {
        verifyUniqueness(request, id);
        Doctor doctor = verifyDoctorById(id);
        doctorMapper.updateEntity(doctor, request);
        Doctor saved = doctorRepository.save(doctor);
        return doctorMapper.toDto(saved);
    }

    public void deleteDoctor(Long id) {
        Doctor doctor = verifyDoctorById(id);
        doctorRepository.delete(doctor);
    }

    private void verifyUniqueness(CreateDoctorRequest request) {
        doctorRepository.findByEmail(request.getEmail())
                .ifPresent(d -> {
                    throw new DoctorAlreadyExistsException("email: " + request.getEmail() + " already exists!");
                });
        doctorRepository.findByPhone(request.getPhone())
                .ifPresent(d -> {
                    throw new DoctorAlreadyExistsException("phone: " + request.getPhone() + " already exists!");
                });
        doctorRepository.findByPwzNumber(request.getPwzNumber())
                .ifPresent(d -> {
                    throw new DoctorAlreadyExistsException("pwzNumber: " + request.getPwzNumber() + " already exists!");
                });
    }

    private void verifyUniqueness(UpdateDoctorRequest request, Long id) {
        verifyDoctorById(id);
        Optional<Doctor> existing = doctorRepository.findByEmail(request.getEmail());
        if (existing.isPresent() && !Objects.equals(existing.get().getId(), id)) {
            throw new DoctorAlreadyExistsException("email: " + request.getEmail() + " already exists!");
        }
        existing = doctorRepository.findByPhone(request.getPhone());
        if (existing.isPresent() && !Objects.equals(existing.get().getId(), id)) {
            throw new DoctorAlreadyExistsException("phone: " + request.getPhone() + " already exists!");
        }
    }

    private Doctor verifyDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException(id));
    }
}
