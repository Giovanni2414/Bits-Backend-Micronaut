package edu.co.icesi.mapper;


import edu.co.icesi.dto.SessionDTO;
import edu.co.icesi.model.Session;
import jakarta.inject.Singleton;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "jsr330")
@Singleton
public interface SessionMapper {


    Session toSession(SessionDTO sessionDTO);

    SessionDTO toSessionDTO(Session session);

    default String fromUUID(UUID uuid) {
        return uuid.toString();
    }

    default UUID fromUUID(String uuid) {
        return UUID.fromString(uuid);
    }

}
