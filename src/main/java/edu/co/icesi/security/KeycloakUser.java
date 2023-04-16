package edu.co.icesi.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakUser {
    private String email;
    private String username;
    private List<String> roles;
    private String token;
}
