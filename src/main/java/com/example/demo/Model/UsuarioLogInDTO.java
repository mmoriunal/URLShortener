package com.example.demo.Model;

/*Un DTO (Data Transfer Object) es un patrón de diseño utilizado en el desarrollo de software 
para transferir datos entre diferentes componentes o capas de una aplicación. 
Su objetivo principal es encapsular y transportar datos de manera eficiente y estructurada, 
evitando la exposición directa de la estructura interna de los objetos. */

public class UsuarioLogInDTO {
    // Usare esta clase principalmente para recibir las entradas (LinkOriginales, Caducidad, PatronPersonalizado ) - (POST)
    // Luego puedo codificarlos en una clase de Service...
    private String username;
    private String password;  

    public UsuarioLogInDTO(String username, String password) {
        this.username = username; this.password = password;
    }
    public UsuarioLogInDTO() {} // NULL para tratarlo como exepción


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
        return "UsuarioLogInDTO: " + username + "\n" + "Contrasena=" + password ;
    }
}
