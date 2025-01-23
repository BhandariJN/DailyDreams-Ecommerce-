package com.dailydreams.dailydreams.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotNull(message = "first Name Can not be Null")
    private String firstName;
    @NotNull(message = "Last Name Can not be null")
    private String lastName;
    @NotNull(message = "email can not be null")
    @Email(message = "Invalid email format")
    private String email;
    @NotNull(message = "Password Can not be null")
    @Size(min = 5, message = "password can must contain more than 5 char")
    private String password;
}
