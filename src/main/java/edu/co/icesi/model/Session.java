package edu.co.icesi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    @Id
    @Column(name = "session_id")
    private UUID sessionId;

    String name;

    @Column(name = "har_file_path")
    String harFilePath;

    @Column(name = "creation_date")
    LocalDateTime creationDate;
}
