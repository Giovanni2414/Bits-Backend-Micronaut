package edu.co.icesi.api;

import edu.co.icesi.dto.SessionDTO;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/sessions")
public interface SessionAPI {

    @Post
    SessionDTO createSession(SessionDTO sessionDTO);
}
