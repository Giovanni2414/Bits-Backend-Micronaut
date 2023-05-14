package edu.co.icesi.repository;

import edu.co.icesi.model.Session;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {

    @Query("SELECT e FROM Session e WHERE e.name LIKE :name")
    List<Session> searchSessionName(String name);


}
