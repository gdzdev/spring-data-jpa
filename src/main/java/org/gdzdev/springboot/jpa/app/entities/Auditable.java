package org.gdzdev.springboot.jpa.app.entities;

import lombok.Data;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Data
@Embeddable
public class Auditable {

    @Column(name = "create_up")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        System.out.println("Evento del ciclo de vida de la clase (antes de Guardar)");
    }

    @PreUpdate
    public void preUpdate() {
        this.updateAt = LocalDateTime.now();
        System.out.println("Evento del ciclo de vida de la clase (antes de Actualizar)");
    }
}
