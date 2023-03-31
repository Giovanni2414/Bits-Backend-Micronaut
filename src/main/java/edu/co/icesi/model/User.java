package edu.co.icesi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID userId;

    private String username;

    private String email;

    private String password;

    private String organizationName;

    private String firstname;

    private String lastname;

    @PrePersist
    public void generateUUID(){ this.userId = UUID.randomUUID(); }
}
