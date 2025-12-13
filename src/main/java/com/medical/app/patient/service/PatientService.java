package com.medical.app.patient.service;

import com.medical.app.doctor.dto.DoctorDto;
import com.medical.app.doctor.dto.UpdateDoctorRequest;
import com.medical.app.doctor.entity.Doctor;
import com.medical.app.patient.dto.CreatePatientRequest;
import com.medical.app.patient.dto.PatientDto;
import com.medical.app.patient.dto.UpdatePatientRequest;
import com.medical.app.patient.entity.Patient;
import com.medical.app.patient.exception.PatientAlreadyExistsException;
import com.medical.app.patient.exception.PatientNotFoundException;
import com.medical.app.patient.mapper.PatientMapper;
import com.medical.app.patient.repository.PatientRepository;
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
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final UserRepository userRepository;
    private final UserFactory userFactory;

    public PatientDto createPatient(CreatePatientRequest request) {
        verifyPatientUniqueness(request);
        verifyUserUniqueness(request);
        User user = userFactory.createPatientUser(request.getEmail(), request.getPassword());
        User savedUser = userRepository.save(user);
        Patient patient = patientMapper.toEntity(request);
        patient.setUser(savedUser);
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toDto(savedPatient);
    }

    public PatientDto getPatientById(Long id) {
        return patientMapper.toDto(verifyPatientById(id));
    }

    public List<PatientDto> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .toList();
    }

    public PatientDto updatePatient(Long id, UpdatePatientRequest request) {
        verifyPatientUniqueness(request, id);
        Patient patient = verifyPatientById(id);
        patientMapper.updateEntity(patient, request);
        Patient saved = patientRepository.save(patient);
        return patientMapper.toDto(saved);
    }

    public void deletePatient(Long id) {
        Patient patient = verifyPatientById(id);
        patient.getUser().setActive(false);
        userRepository.save(patient.getUser());
        patientRepository.delete(patient);
    }

    private void verifyPatientUniqueness(CreatePatientRequest request) {
        patientRepository.findByPhone(request.getPhone())
                .ifPresent(p -> {
                    throw new PatientAlreadyExistsException("phone: " + request.getPhone() + " already exists!");
                });
        patientRepository.findByPesel(request.getPesel())
                .ifPresent(p -> {
                    throw new PatientAlreadyExistsException("pesel: " + request.getPesel() + " already exists!");
                });
    }

    private void verifyPatientUniqueness(UpdatePatientRequest request, Long id) {
        verifyPatientById(id);
        Optional<Patient> existing = patientRepository.findByPhone(request.getPhone());
        if (existing.isPresent() && !Objects.equals(existing.get().getId(), id)) {
            throw new PatientAlreadyExistsException("phone: " + request.getPhone() + " already exists!");
        }
    }

    private void verifyUserUniqueness(CreatePatientRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(d -> {
                    throw new UserAlreadyExistsException("email: " + request.getEmail() + " already exists!");
                });
    }

    private Patient verifyPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }
}
