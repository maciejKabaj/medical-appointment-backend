package com.medical.app.doctor.controller;

import com.medical.app.doctor.dto.CreateDoctorRequest;
import com.medical.app.doctor.dto.DoctorDto;
import com.medical.app.doctor.dto.UpdateDoctorRequest;
import com.medical.app.doctor.entity.Specialization;
import com.medical.app.doctor.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorDto> createDoctor(@Valid @RequestBody CreateDoctorRequest request) {
        DoctorDto dto = doctorService.createDoctor(request);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long doctorId) {
        DoctorDto dto = doctorService.getDoctorById(doctorId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        List<DoctorDto> dto = doctorService.getAllDoctors();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/find")
    public ResponseEntity<List<DoctorDto>> getDoctorsBySpecialization(@RequestParam Specialization specialization) {
        return ResponseEntity.ok(doctorService.getDoctorsBySpecialization(specialization));
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<DoctorDto> updateDoctor(@PathVariable Long doctorId, @Valid @RequestBody UpdateDoctorRequest request) {
        return ResponseEntity.ok(doctorService.updateDoctor(doctorId, request));
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long doctorId) {
        doctorService.deleteDoctor(doctorId);
        return ResponseEntity.ok().build();
    }
}
