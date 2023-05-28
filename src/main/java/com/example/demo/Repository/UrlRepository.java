package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;  
import org.springframework.stereotype.Repository;
// JpaRepository, que es una interfaz proporcionada por Spring Data JPA para realizar operaciones CRUD

import com.example.demo.Model.Url;

@Repository                   // < Entidad a buscar, tipo de identificador (key) >
public interface UrlRepository extends JpaRepository<Url,Long> {

    // Cuando se llama a este método, Spring Data JPA generará automáticamente una consulta SQL 
    public Url findByShortLink(String shortLink);

}

    /* En escencia solo necesito implementar un metodo que me permita hacer 
     * una QUERY basado en el codigo (shortlink) de cada URL original.
     * 
     * La usare como entrada para un metodo GET en el controlador que 
     * extraiga directo de la base de datos y obtenga y redirija al OriginalUrl.
     */

/* Failed to create query for method public abstract com.example.demo.Model.Url 
com.example.demo.Repository.UrlRepository.findByCode(java.lang.String); 
No property 'code' found for type 'Url' 

Al parecer el nombre del metodo sebe incluir un atributo de la DB directamente
*/