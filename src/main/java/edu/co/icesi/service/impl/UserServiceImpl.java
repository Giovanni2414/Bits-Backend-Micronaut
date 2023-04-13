package edu.co.icesi.service.impl;

import edu.co.icesi.model.User;
import edu.co.icesi.repository.UserRepository;
import edu.co.icesi.service.UserService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Singleton
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

}
