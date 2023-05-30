package com.example.demo.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;


/* Una entidad persistente representa una tabla en una base de datos relacional y se utiliza 
para mapear objetos de Java a registros en dicha tabla. Cada instancia de una entidad persistente 
representa una fila en la tabla correspondiente.
Al marcar una clase con la anotación @Entity, se le indica a JPA que la clase debe 
ser mapeada a una tabla en la base de datos. [Java Persistence API (JPA)] 

-> Posteriormente creare una clase 'REPOSITORIO' para administrar la base de datos. <- */

@Entity
public class Url { // Usare esta clase para alimentar la Base ya con el LinkOriginal asociado a un ShortLink (GET)
    // Llave primaria generada automaticamente (creciente)
    @Id 
    @GeneratedValue
    private long id;

    @Lob // Atributos de una entidad que deben ser tratados como un objeto grande.
    private String title;
    private String originalUrl;
    private String shortLink;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;


    public Url(String title, long id, String originalUrl, String shortLink, LocalDateTime creationDate, LocalDateTime expirationDate) {
        this.title = title;
        this.id = id; this.originalUrl = originalUrl; this.shortLink = shortLink;
        this.creationDate = creationDate; this.expirationDate = expirationDate;
    }
    public Url() {} // En caso que no entre nada da null y se maneja como un 404 error


    // Getters y Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

    public String getShortLink() { return shortLink; }
    public void setShortLink(String shortLink) { this.shortLink = shortLink; }

    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }

    public LocalDateTime getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDateTime expirationDate) { this.expirationDate = expirationDate; }


    @Override //super -> Object.
    public String toString() {
        return "Url = { " + id + ", " + title + ", " + originalUrl + ", "  + shortLink + ", " 
                + creationDate + ", "  + expirationDate + " }" ;
    }
}
