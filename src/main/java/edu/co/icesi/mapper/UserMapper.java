package edu.co.icesi.mapper;

import edu.co.icesi.dto.UserDTO;
import edu.co.icesi.model.User;
import jakarta.inject.Singleton;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jrs330")
@Singleton
public interface UserMapper {

    User fromDTO(UserDTO userDTO);

    UserDTO fromUser(User UserDTO);
}
