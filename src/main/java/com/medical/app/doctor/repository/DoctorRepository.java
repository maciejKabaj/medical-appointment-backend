package com.medical.app.doctor.repository;

import com.medical.app.doctor.entity.Doctor;
import com.medical.app.doctor.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByPhone(String phone);
    Optional<Doctor> findByPwzNumber(String pwzNumber);
    List<Doctor> findBySpecialization(Specialization specialization);
}
