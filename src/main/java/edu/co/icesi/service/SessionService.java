package edu.co.icesi.service;

import edu.co.icesi.model.Session;

import java.util.List;
import java.util.UUID;

public interface SessionService {
    Session createSession(Session session);

    Session getSession(UUID id);

    List<Session> getAllSessions();

    List<Session> getSessionsPaginated(int offset, int limit);
}
