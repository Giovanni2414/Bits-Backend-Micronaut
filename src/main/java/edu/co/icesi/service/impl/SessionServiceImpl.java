package edu.co.icesi.service.impl;

import edu.co.icesi.constants.ErrorConstants;
import edu.co.icesi.error.exception.VarxenPerformanceError;
import edu.co.icesi.error.exception.VarxenPerformanceException;
import edu.co.icesi.model.Session;
import edu.co.icesi.model.User;
import edu.co.icesi.repository.SessionRepository;
import edu.co.icesi.service.SessionService;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Singleton
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {

    @Inject
    private SessionRepository sessionRepository;

    @Override
    public Session createSession(Session session) {

        //TODO remove this and change for real logged-user id
        UUID userID = UUID.fromString("f4e86d73-12a0-4d8d-8ea1-6c7e6b6b4402");

        System.out.println(session.getBlob().getBlobId());

        User user = User.builder().userId(userID).build();

        session.setUser(user);

        try {
            return sessionRepository.save(session);
        } catch (Exception e) {
            throw new VarxenPerformanceException(HttpStatus.BAD_REQUEST, new VarxenPerformanceError(ErrorConstants.SESSION_NOT_CREATED, ErrorConstants.SESSION_NOT_CREATED));
        }
    }

    @Override
    public Session getSession(UUID id) {
        return sessionRepository.findById(id).orElseThrow(() -> new VarxenPerformanceException(HttpStatus.NOT_FOUND, new VarxenPerformanceError(ErrorConstants.SESSION_NOT_FOUND, ErrorConstants.SESSION_NOT_FOUND)));
    }

    @Override
    public List<Session> getAllSessions() {
        return new ArrayList<>(sessionRepository.findAll());
    }

    @Override
    public List<Session> getSessionsPaginated(int offset, int limit) {
        return sessionRepository.findAll(Pageable.from(offset, limit)).getContent();
    }

    @Override
    public Session deleteSession(UUID sessionId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteSession'");
    }
}
