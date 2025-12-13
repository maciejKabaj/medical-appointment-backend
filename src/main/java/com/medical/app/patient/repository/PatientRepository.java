package com.medical.app.patient.repository;

import com.medical.app.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByPhone(String phone);
    Optional<Patient> findByPesel(String pesel);
}
