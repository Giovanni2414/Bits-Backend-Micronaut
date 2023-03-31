package edu.co.icesi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID userId;

    private String username;

    private String email;

    private String password;

    private String organizationName;

    private String firstname;

    private String lastname;

}
