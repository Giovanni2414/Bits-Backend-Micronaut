package edu.co.icesi.mapper;

import edu.co.icesi.dto.UserDTO;
import edu.co.icesi.model.User;
import jakarta.inject.Singleton;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "jsr330")
@Singleton
public interface UserMapper {

    User fromDTO(UserDTO userDTO);

    UserDTO fromUser(User user);

    default String fromUUID(UUID uuid) {
        return uuid.toString();
    }

    default UUID fromUUID(String uuid) {
        return UUID.fromString(uuid);
    }
}
