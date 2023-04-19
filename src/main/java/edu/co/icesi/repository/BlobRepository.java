package edu.co.icesi.repository;

import edu.co.icesi.model.Blob;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.UUID;

@Repository
public interface BlobRepository extends CrudRepository<Blob, UUID> {
}
