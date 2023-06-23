package edu.co.icesi.service.impl;

import edu.co.icesi.constant.CodesError;
import edu.co.icesi.error.exception.VarxenPerformanceError;
import edu.co.icesi.error.exception.VarxenPerformanceException;
import edu.co.icesi.model.User;
import edu.co.icesi.repository.UserRepository;
import edu.co.icesi.security.AdminRequest;
import edu.co.icesi.service.UserService;
import io.micronaut.http.HttpStatus;
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
            if(userRepository.findByUsername(user.getUsername()).isPresent()){
                throw new VarxenPerformanceException(HttpStatus.BAD_REQUEST, new VarxenPerformanceError(CodesError.REGISTERED_USERNAME.getCode(), CodesError.REGISTERED_USERNAME.getMessage()));
            }else if(userRepository.findByEmail(user.getEmail()).isPresent()){
                throw new VarxenPerformanceException(HttpStatus.BAD_REQUEST, new VarxenPerformanceError(CodesError.REGISTERED_MAIL.getCode(), CodesError.REGISTERED_MAIL.getMessage()));
            }
            User saved = userRepository.save(user);
            adminRequest.registerUserKeycloak(user);
            return saved;
        } catch (ConstraintViolationException cve) {
            //TODO pendiente lanzar excepción
            return null;
        }
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(()-> new VarxenPerformanceException(HttpStatus.BAD_REQUEST, new VarxenPerformanceError(CodesError.USER_NOT_FOUND.getCode(),CodesError.USER_NOT_FOUND.getMessage())));
    }
}
