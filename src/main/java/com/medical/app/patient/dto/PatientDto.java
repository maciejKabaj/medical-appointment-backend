package com.medical.app.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PatientDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
}
