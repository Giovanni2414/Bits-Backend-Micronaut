package edu.co.icesi.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Introspected
public class UserDTO {

    private UUID userId;

    private String username;

    private String email;

    private String password;

    private String organizationName;

    private String firstname;

    private String lastname;

}
