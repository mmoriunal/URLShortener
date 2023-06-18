package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;




/* 
Una entidad persistente representa una tabla en una base de datos relacional y se utiliza 
para mapear objetos de Java a registros en dicha tabla. Cada instancia de una entidad persistente 
representa una fila en la tabla correspondiente.
Al marcar una clase con la anotación @Entity, se le indica a JPA que la clase debe 
ser mapeada a una tabla en la base de datos. [Java Persistence API (JPA)] 

-> Posteriormente creare una clase 'REPOSITORIO' para administrar la base de datos. <- 
*/

@Entity
public class Url { // Usare esta clase para alimentar la Base ya con el LinkOriginal asociado a un ShortLink (GET)
    // Llave primaria generada automaticamente (creciente)
    @Id 
    @GeneratedValue 
    private long id;

    @Lob // Atributos de una entidad que deben ser tratados como un objeto grande.
    private String originalUrl;
    private String shortLink;
    private String creationDate;
    private String expirationDate;
    private String userLink;


    public Url(long id, String originalUrl, String shortLink, String creationDate, String expirationDate, String userLink) {
        this.id = id; this.originalUrl = originalUrl; this.shortLink = shortLink;
        this.creationDate = creationDate; this.expirationDate = expirationDate;
        this.userLink = userLink;
    }
    public Url() {} // En caso que no entre nada da null y se maneja como un 404 error


    // Getters y Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

    public String getShortLink() { return shortLink; }
    public void setShortLink(String shortLink) { this.shortLink = shortLink; }

    public String getCreationDate() { return creationDate; }
    public void setCreationDate(String creationDate) { this.creationDate = creationDate; }

    public String getExpirationDate() { return expirationDate; }
    public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }

    public String getUserLink() {
        return userLink;
    }
    public void setUserLink(String userLink) {
        this.userLink = userLink;
    }

    @Override //super -> Object.
    public String toString() {
        return "Url = { " + id + ", " + originalUrl + ", "  + shortLink + ", " 
                + creationDate + ", "  + expirationDate + " }" ;
    }
}
