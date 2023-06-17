package com.example.demo.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* Una entidad persistente representa una tabla en una base de datos relacional y se utiliza 
para mapear objetos de Java a registros en dicha tabla. Cada instancia de una entidad persistente 
representa una fila en la tabla correspondiente.
Al marcar una clase con la anotación @Entity, se le indica a JPA que la clase debe 
ser mapeada a una tabla en la base de datos. [Java Persistence API (JPA)] 

-> Posteriormente creare una clase 'REPOSITORIO' para administrar la base de datos. <- */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "usuarios")
public class Usuario {
    // Llave primaria generada automaticamente (creciente)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, name = "username")
    private String username;

    @Column(nullable = false, name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = {
            @JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") })
    private List<Role> roles = new ArrayList<>();

    @Override // super -> Object.
    public String toString() {
        return "Usuario = { " + id + ", " + username + ", " + password + " }";
    }
}
