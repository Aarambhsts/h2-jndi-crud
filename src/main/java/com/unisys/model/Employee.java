package com.unisys.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Long id;
    @NotNull(message = "Employee name is required.")
    @Size(min = 1, message = "Name cannot be empty.")
    private String name;
    @Email(message = "Invalid email format.")
    @NotNull(message = "Email is required.")
    private String email;
}
