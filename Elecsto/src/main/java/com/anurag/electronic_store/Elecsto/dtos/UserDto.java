package com.anurag.electronic_store.Elecsto.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min = 3,max = 15,message = "Invalid name!")
    @Pattern(regexp = "^[A-Za-z]+( [A-Za-z]+)*$", message = "Name must only contain alphabetic characters!")
    private String name;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email must be in a valid format!")
    @NotBlank(message = "Email is required!")
    private String email;

    @NotBlank(message = "Password is required!")
    private String password;

    @Size(min = 4,max = 6,message = "Invalid gender!")
    private String gender;

    @NotBlank(message = "Please add something in about field!")
    private String about;

    private String imageName;
}
