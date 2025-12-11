package com.medical.app.doctor.dto;

import com.medical.app.doctor.entity.Specialization;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDoctorRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private Specialization specialization;

    @NotBlank
    private String phone;

    @Email @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    @Size(min = 7, max = 7)
    private String pwzNumber;

    private String description;
}
