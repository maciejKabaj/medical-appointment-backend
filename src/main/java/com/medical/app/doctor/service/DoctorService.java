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
import com.medical.app.user.entity.User;
import com.medical.app.user.exception.UserAlreadyExistsException;
import com.medical.app.user.factory.UserFactory;
import com.medical.app.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final UserFactory userFactory;

    public DoctorDto createDoctor(CreateDoctorRequest request) {
        verifyDoctorUniqueness(request);
        verifyUserUniqueness(request);
        User user = userFactory.createDoctorUser(request.getEmail(), request.getPassword());
        User savedUser = userRepository.save(user);
        Doctor doctor = doctorMapper.toEntity(request);
        doctor.setUser(savedUser);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toDto(savedDoctor);
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
        verifyDoctorUniqueness(request, id);
        Doctor doctor = verifyDoctorById(id);
        doctorMapper.updateEntity(doctor, request);
        Doctor saved = doctorRepository.save(doctor);
        return doctorMapper.toDto(saved);
    }

    public void deleteDoctor(Long id) {
        Doctor doctor = verifyDoctorById(id);
        doctorRepository.delete(doctor);
    }

    private void verifyDoctorUniqueness(CreateDoctorRequest request) {
        doctorRepository.findByPhone(request.getPhone())
                .ifPresent(d -> {
                    throw new DoctorAlreadyExistsException("phone: " + request.getPhone() + " already exists!");
                });
        doctorRepository.findByPwzNumber(request.getPwzNumber())
                .ifPresent(d -> {
                    throw new DoctorAlreadyExistsException("pwzNumber: " + request.getPwzNumber() + " already exists!");
                });
    }

    private void verifyDoctorUniqueness(UpdateDoctorRequest request, Long id) {
        verifyDoctorById(id);
        Optional<Doctor> existing = doctorRepository.findByPhone(request.getPhone());
        if (existing.isPresent() && !Objects.equals(existing.get().getId(), id)) {
            throw new DoctorAlreadyExistsException("phone: " + request.getPhone() + " already exists!");
        }
    }

    private Doctor verifyDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException(id));
    }

    private void verifyUserUniqueness(CreateDoctorRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(d -> {
                    throw new UserAlreadyExistsException("email: " + request.getEmail() + " already exists!");
                });
    }
}
