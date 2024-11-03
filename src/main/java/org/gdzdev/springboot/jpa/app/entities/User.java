package org.gdzdev.springboot.jpa.app.entities;

import lombok.*;
import jakarta.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    @Embedded
    private Auditable auditable = new Auditable();

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
