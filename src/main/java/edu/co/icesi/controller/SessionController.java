package edu.co.icesi.controller;

import edu.co.icesi.api.SessionAPI;
import edu.co.icesi.dto.SessionDTO;
import edu.co.icesi.mapper.SessionMapper;
import edu.co.icesi.service.SessionService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller("/sessions")
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
    public List<SessionDTO> getAllSessions(@QueryValue Optional<Integer> offset, @QueryValue Optional<Integer> limit) {

        if (offset.isPresent() && limit.isPresent()) {
            return sessionService.getSessionsPaginated(offset.get(), limit.get()).stream().map(sessionMapper::toSessionDTO).collect(Collectors.toList());
        }

        return sessionService.getAllSessions().stream().map(sessionMapper::toSessionDTO).collect(Collectors.toList());
    }
}
