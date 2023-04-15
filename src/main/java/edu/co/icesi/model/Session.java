package edu.co.icesi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table(name = "session")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    @Id
    @Column(name = "session_id")
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID sessionId;

    private String name;

    @Column(name = "har_file_path")
    private String harFilePath;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void generated() {
        this.sessionId = UUID.randomUUID();
        this.creationDate = LocalDateTime.now();
    }
}
