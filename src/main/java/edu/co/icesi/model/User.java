package edu.co.icesi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Data
@Table(name = "`users`")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @Column(name = "user_id")
    private UUID userId;

    private String username;

    private String email;

    private String password;

    @Column(name = "organization_name")
    private String organizationName;

    private String firstname;

    private String lastname;

    @PrePersist
    public void generateUUID(){ this.userId = UUID.randomUUID(); }
}
