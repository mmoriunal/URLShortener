package com.example.demo.Model;

/*Un DTO (Data Transfer Object) es un patrón de diseño utilizado en el desarrollo de software 
para transferir datos entre diferentes componentes o capas de una aplicación. 
Su objetivo principal es encapsular y transportar datos de manera eficiente y estructurada, 
evitando la exposición directa de la estructura interna de los objetos. */

public class UrlDTO {
    // Usare esta clase principalmente para recibir las entradas (LinkOriginales, Caducidad, PatronPersonalizado ) - (POST)
    // Luego puedo codificarlos en una clase de Service...
    private String url;
    private String exp;  //Default: 72h



    public UrlDTO(String url, String exp) {
        this.url = url; this.exp = exp;
    }
    public UrlDTO() {} // NULL para tratarlo como exepción


    // Get and Set
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getExp() { return exp; }
    public void setExp(String exp) { this.exp = exp;}

    @Override
    public String toString() {
        return "UrlDTO: " + url + "\n" + "ExpirationDate='" + exp ;
    }
}
