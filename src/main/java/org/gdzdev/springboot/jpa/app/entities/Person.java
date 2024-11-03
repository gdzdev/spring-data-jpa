package org.gdzdev.springboot.jpa.app.entities;

import lombok.*;
import jakarta.persistence.*;

@Data
@Entity
@Builder
@Table(name = "persons")
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "programming_language")
    private String programmingLanguage;

    @Embedded
    private Auditable audit = new Auditable();
}
