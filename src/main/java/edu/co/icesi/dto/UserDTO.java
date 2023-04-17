package edu.co.icesi.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Introspected
public class UserDTO {


    private UUID userId;

    @NotBlank
    @NotNull
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])[A-Za-z\\d._%+-]+@[A-Za-z\\d.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,28}$")
    private String password;

    @NotNull
    private String organizationName;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

}
