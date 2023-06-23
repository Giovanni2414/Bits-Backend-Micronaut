package edu.co.icesi.service;

import edu.co.icesi.dto.UserDTO;
import edu.co.icesi.model.User;
import io.micronaut.http.annotation.Body;

public interface UserService {
    User createUser(User user);

    User getUser(String username);
}
