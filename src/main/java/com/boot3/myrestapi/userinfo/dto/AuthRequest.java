package com.boot3.myrestapi.userinfo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequest {
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email not be less than two characters")
    @Email
    private String email;
    @NotNull(message = "Password cannot be null")
    @Size(min = 2, message = "Password must be equals or grater than 8 characters")
    private String password;
}
