package com.example.demo.Model;

/*Un DTO (Data Transfer Object) es un patrón de diseño utilizado en el desarrollo de software 
para transferir datos entre diferentes componentes o capas de una aplicación. 
Su objetivo principal es encapsular y transportar datos de manera eficiente y estructurada, 
evitando la exposición directa de la estructura interna de los objetos. */

public class UrlDTO {
    // Usare esta clase principalmente para recibir las entradas (LinkOriginales, Caducidad, PatronPersonalizado ) - (POST)
    // Luego puedo codificarlos en una clase de Service...

    private String title;
    private String url;
    private String exp;  //Default: 72h


    public UrlDTO(String title, String url, String exp) {
        this.title = title;
        this.url = url; this.exp = exp;
    }
    public UrlDTO() {} // NULL para tratarlo como excepción


    // Get and Set

    public String getTitle() { return title; }
    public void setTitle(String title) {
        this.title = (title.length()==0) ? "DefaultName" : title; 
    }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getExpirationDate() { return exp; }
    public void setExpirationDate(String exp) { this.exp = exp; }


    @Override
    public String toString() {
        return "Titulo: " + title + "\n" + 
        "UrlDTO: " + url + "\n" + "ExpirationDate= " + exp ;
    }
}
