package edu.co.icesi.mapper;

import edu.co.icesi.dto.PerformanceTestDTO;
import edu.co.icesi.model.PerformanceTest;
import edu.co.icesi.model.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "jsr330")
public interface PerformanceTestMapper {

    @Mapping(source = "sessionId", target = "session.sessionId")
    PerformanceTest toPerformanceTest(PerformanceTestDTO performanceTestDTO);

    @Mapping(source = "session.sessionId", target = "sessionId")
    PerformanceTestDTO toPerformanceTestDTO(PerformanceTest performanceTest);

    default UUID fromSession( Session session){ return session.getSessionId(); };

    default Session toSession(UUID session) { return Session.builder().sessionId(session).build(); }
}
