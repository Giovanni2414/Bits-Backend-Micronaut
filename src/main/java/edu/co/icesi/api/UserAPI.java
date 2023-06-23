package edu.co.icesi.api;

import edu.co.icesi.dto.UserDTO;
import edu.co.icesi.model.User;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

public interface UserAPI {

    @Post()
    UserDTO createUser(@Body UserDTO userDTO);

    @Get("/{username}")
    UserDTO getUser(String username);

}
