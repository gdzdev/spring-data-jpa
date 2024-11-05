package org.gdzdev.springboot.jpa.app.entities;

import lombok.*;

@Data
@AllArgsConstructor
public class UserDto {

    private String username;
    private String email;
}
