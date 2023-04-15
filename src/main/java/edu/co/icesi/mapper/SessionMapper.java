package edu.co.icesi.mapper;


import edu.co.icesi.dto.SessionDTO;
import edu.co.icesi.model.Session;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330")
public interface SessionMapper {
    Session toSession(SessionDTO sessionDTO);

    SessionDTO toSessionDTO(Session session);
}
