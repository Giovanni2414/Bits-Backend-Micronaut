package edu.co.icesi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table(name = "blobs")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blob implements Serializable {
    @Id
    @Column(name = "blob_id")
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID blobId;


    @Column(name = "relative_path", nullable = false, unique = true)
    private String relativePath;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @PrePersist
    public void generated() {
        this.creationDate = LocalDateTime.now();
    }
}
