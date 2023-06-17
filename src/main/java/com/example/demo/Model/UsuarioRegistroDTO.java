package com.example.demo.Model;
//tiene mas sentido si trabaja con otro atributo diferente a username y contrasena (email,etc)
//por ahora es igual al de login
public class UsuarioRegistroDTO {
    
    private String username;
    private String password;  

    public UsuarioRegistroDTO(String username, String password) {
        this.username = username; this.password = password;
    }
    public UsuarioRegistroDTO() {} // NULL para tratarlo como exepción


    // Get and Set
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "UsuarioRegistroDTO: " + username + "\n" + "Contrasena=" + password ;
    }
}