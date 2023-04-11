package edu.co.icesi.service.impl;

import edu.co.icesi.constants.ErrorConstants;
import edu.co.icesi.error.exception.VarxenPerformanceError;
import edu.co.icesi.error.exception.VarxenPerformanceException;
import edu.co.icesi.model.Session;
import edu.co.icesi.repository.SessionRepository;
import edu.co.icesi.service.SessionService;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {

    @Inject
    private SessionRepository sessionRepository;

    @Override
    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public Session getSession(UUID id) {
        return sessionRepository.findById(id).orElseThrow(() -> new VarxenPerformanceException(HttpStatus.NOT_FOUND, new VarxenPerformanceError(ErrorConstants.SESSION_NOT_FOUND, ErrorConstants.SESSION_NOT_FOUND)));
    }

    @Override
    public List<Session> getAllSessions() {
        return StreamSupport.stream(sessionRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
