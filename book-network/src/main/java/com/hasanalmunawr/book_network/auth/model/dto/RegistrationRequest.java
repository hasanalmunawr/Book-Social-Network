package com.hasanalmunawr.book_network.auth.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(

        @NotEmpty(message = "username is mandatory")
        @NotNull(message = "username is mandatory")
        @JsonProperty(namespace = "username")
        String username,
     /*   @JsonProperty(namespace = "last_name")
        String lastName,*/

        @Email(message = "Email is not well formatted")
        @NotEmpty(message = "Email is mandatory")
        @NotNull(message = "Email is mandatory")
        String email,

        @NotEmpty(message = "Password is mandatory")
        @NotNull(message = "Password is mandatory")
        @Size(min = 8, message = "Password should be 8 characters long minimum")
        String password,

        @NotEmpty(message = "Role is mandatory")
        @NotNull(message = "Role is mandatory")
        String role
) {
}
