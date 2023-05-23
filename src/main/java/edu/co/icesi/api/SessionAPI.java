package edu.co.icesi.api;

import edu.co.icesi.dto.SessionDTO;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Client
public interface SessionAPI {

    SessionDTO createSession(SessionDTO sessionDTO);

    SessionDTO getSession(UUID id);

    List<SessionDTO> getAllSessions(Optional<Integer> offset, Optional<Integer> limit);

    SessionDTO deleteSession(UUID sessionId);

    @Get("/search/{name}")
    List<SessionDTO> searchSessionbyName(String name);
}
