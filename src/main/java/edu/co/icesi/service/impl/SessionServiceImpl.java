package edu.co.icesi.service.impl;

import edu.co.icesi.constant.CodesError;
import edu.co.icesi.error.exception.VarxenPerformanceError;
import edu.co.icesi.error.exception.VarxenPerformanceException;
import edu.co.icesi.model.Session;
import edu.co.icesi.model.User;
import edu.co.icesi.repository.BlobRepository;
import edu.co.icesi.repository.SessionRepository;
import edu.co.icesi.repository.UserRepository;
import edu.co.icesi.service.BlobService;
import edu.co.icesi.service.SessionService;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {

    @Inject
    private SessionRepository sessionRepository;

    @Inject
    private BlobService blobService;

    @Inject
    private BlobRepository blobRepository;

    @Inject
    private UserRepository userRepository;


    @Override
    public Session createSession(Session session) {
        User user = userRepository.findByUsername(session.getUser().getUsername()).orElseThrow(() -> new VarxenPerformanceException(HttpStatus.NOT_FOUND, new VarxenPerformanceError(CodesError.USER_NOT_FOUND.getCode(), CodesError.USER_NOT_FOUND.getMessage())));

        blobRepository.findById(session.getBlob().getBlobId()).orElseThrow(()-> new VarxenPerformanceException(HttpStatus.BAD_REQUEST, new VarxenPerformanceError(CodesError.SESSION_NOT_CREATED.getCode(), CodesError.SESSION_NOT_CREATED.getMessage())));

        session.setUser(user);

        if(sessionRepository.findByName(session.getName()).isPresent()){
            blobService.deleteBlob(session.getBlob().getBlobId());
            throw new VarxenPerformanceException(HttpStatus.BAD_REQUEST, new VarxenPerformanceError(CodesError.SESSION_ALREADY_EXIST.getCode(), CodesError.SESSION_ALREADY_EXIST.getMessage()));
        }

        try {
            return sessionRepository.save(session);
        } catch (Exception e) {
            throw new VarxenPerformanceException(HttpStatus.BAD_REQUEST, new VarxenPerformanceError(CodesError.SESSION_NOT_CREATED.getCode(), CodesError.SESSION_NOT_CREATED.getMessage()));
        }
    }

    @Override
    public Session getSession(UUID id) {
        return sessionRepository.findById(id).orElseThrow(() -> new VarxenPerformanceException(HttpStatus.NOT_FOUND, new VarxenPerformanceError(CodesError.SESSION_NOT_FOUND.getCode(), CodesError.SESSION_NOT_FOUND.getMessage())));
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
        Optional<Session> session = sessionRepository.findById(sessionId);

        if (session.isPresent()) {
            sessionRepository.deleteById(sessionId);
            blobService.deleteBlob(session.get().getBlob().getBlobId());
            return session.get();
        } else {
            throw new VarxenPerformanceException(HttpStatus.BAD_REQUEST, new VarxenPerformanceError(CodesError.SESSION_NOT_FOUND.getCode(), CodesError.SESSION_NOT_FOUND.getMessage()));
        }
    }

    @Override
    public List<Session> searchSessionName(String name) {
        return sessionRepository.searchSessionName(name);
    }
}
