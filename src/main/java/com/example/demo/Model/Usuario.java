package com.example.demo.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/* Una entidad persistente representa una tabla en una base de datos relacional y se utiliza 
para mapear objetos de Java a registros en dicha tabla. Cada instancia de una entidad persistente 
representa una fila en la tabla correspondiente.
Al marcar una clase con la anotación @Entity, se le indica a JPA que la clase debe 
ser mapeada a una tabla en la base de datos. [Java Persistence API (JPA)] 

-> Posteriormente creare una clase 'REPOSITORIO' para administrar la base de datos. <- */

@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class Usuario {
    // Llave primaria generada automaticamente (creciente)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
    
    public Usuario(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Usuario() {
    } // En caso que no entre nada da null y se maneja como un 404 error

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    @Override // super -> Object.
    public String toString() {
        return "Usuario = { " + id + ", " + username + ", " + password + " }";
    }
}
