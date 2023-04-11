package edu.co.icesi.repository;

import edu.co.icesi.model.Session;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Slice;
import io.micronaut.data.repository.CrudRepository;

import java.util.UUID;

@Repository
public interface SessionRepository extends CrudRepository<Session, UUID> {

    Slice<Session> listOrderByCreationDate(Pageable pageable);
}
