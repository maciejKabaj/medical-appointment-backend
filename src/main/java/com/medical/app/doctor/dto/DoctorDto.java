package com.medical.app.doctor.dto;

import com.medical.app.doctor.entity.Specialization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DoctorDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Specialization specialization;
    private String phone;
    private String description;
}
