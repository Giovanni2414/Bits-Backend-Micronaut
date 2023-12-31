package edu.co.icesi.controller;

import edu.co.icesi.api.SessionAPI;
import edu.co.icesi.constant.CodesError;
import edu.co.icesi.dto.SessionDTO;
import edu.co.icesi.error.exception.VarxenPerformanceError;
import edu.co.icesi.error.exception.VarxenPerformanceException;
import edu.co.icesi.mapper.SessionMapper;
import edu.co.icesi.service.SessionService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller("/sessions")
@Secured(SecurityRule.IS_ANONYMOUS)
public class SessionController implements SessionAPI {


    @Inject
    private SessionMapper sessionMapper;

    @Inject
    private SessionService sessionService;

    @Override
    @Post
    @Produces(MediaType.APPLICATION_JSON)

    public SessionDTO createSession(@Valid @Body SessionDTO sessionDTO) {
        return sessionMapper.toSessionDTO(sessionService.createSession(sessionMapper.toSession(sessionDTO)));
    }

    @Override
    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public SessionDTO getSession(@PathVariable UUID id) {
        return sessionMapper.toSessionDTO(sessionService.getSession(id));
    }

    @Override
    @Get
    public List<SessionDTO> getAllSessions(@QueryValue Optional<Integer> page, @QueryValue Optional<Integer> pageSize) {
        if (page.isPresent() && pageSize.isPresent()) {
            return sessionService.getSessionsPaginated(page.get(), pageSize.get()).stream().map(sessionMapper::toSessionDTO).collect(Collectors.toList());
        }

        return sessionService.getAllSessions().stream().map(sessionMapper::toSessionDTO).collect(Collectors.toList());
    }

    @Override
    public List<SessionDTO> getAllSessionsByUser(String username) {
        return sessionService.getAllSessionsByUser(username).stream().map(sessionMapper::toSessionDTO).collect(
                Collectors.toList());
    }

    @Override
    @Get("/search/{name}")
    public List<SessionDTO> searchSessionbyName(String name) {
        return sessionService.searchSessionName(name).stream().map(sessionMapper::toSessionDTO).collect(
                Collectors.toList());
    }

    @Delete("/{sessionId}")
    public SessionDTO deleteSession(@PathVariable UUID sessionId) {
        return sessionMapper.toSessionDTO(sessionService.deleteSession(sessionId));
    }
}
