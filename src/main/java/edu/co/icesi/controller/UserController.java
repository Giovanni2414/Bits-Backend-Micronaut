package edu.co.icesi.controller;

import edu.co.icesi.api.UserAPI;
import edu.co.icesi.dto.UserDTO;
import edu.co.icesi.mapper.UserMapper;
import edu.co.icesi.service.UserService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller("/users")
@Secured(SecurityRule.IS_ANONYMOUS)
public class UserController implements UserAPI {

    @Inject
    UserService userService;

    @Inject
    UserMapper userMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    }

    @Override
    public String testGet() {
        return "Test Get";
    }
}
