package edu.co.icesi.service.impl;

import edu.co.icesi.model.User;
import edu.co.icesi.repository.UserRepository;
import edu.co.icesi.security.AdminRequest;
import edu.co.icesi.service.UserService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintViolationException;

@AllArgsConstructor
@Singleton
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;

    AdminRequest adminRequest;

    @Override
    public User createUser(User user) {
        try {
            User saved = userRepository.save(user);
            adminRequest.registerUserKeycloak(user);
            return saved;
        } catch (ConstraintViolationException cve) {
            //TODO pendiente lanzar excepción
            return null;
        }
    }
}
