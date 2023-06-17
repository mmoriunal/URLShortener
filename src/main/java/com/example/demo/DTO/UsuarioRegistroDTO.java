package com.example.demo.DTO;
//tiene mas sentido si trabaja con otro atributo diferente a username y contrasena (email,etc)
//por ahora es igual al de login

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRegistroDTO {
    private Long id;

    @NotEmpty(message = "pon userneim")
    private String username;
    @NotEmpty(message = "pon paswor")
    private String password;

    @Override
    public String toString() {
        return "UsuarioRegistroDTO: " + username + "\n" + "Contrasena=" + password ;
    }
}