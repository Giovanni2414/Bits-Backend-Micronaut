package edu.co.icesi.repository;

import edu.co.icesi.model.Blob;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.UUID;

@Repository
public interface BlobRepository extends JpaRepository<Blob, UUID> {
}
