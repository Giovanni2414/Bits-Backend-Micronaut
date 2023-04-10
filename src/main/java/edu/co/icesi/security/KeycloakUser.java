package edu.co.icesi.security;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KeycloakUser {
    private String email;
    private String username;
    private List<String> roles;
}
