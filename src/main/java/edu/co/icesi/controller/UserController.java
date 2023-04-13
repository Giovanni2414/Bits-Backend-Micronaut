package edu.co.icesi.controller;

import edu.co.icesi.api.UserAPI;
import edu.co.icesi.dto.UserDTO;
import edu.co.icesi.mapper.UserMapper;
import edu.co.icesi.service.UserService;
import io.micronaut.http.annotation.Controller;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller("/User")
public class UserController implements UserAPI {

    UserMapper userMapper;

    UserService userService;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    }
}
