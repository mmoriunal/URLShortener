package com.example.demo.Service;
import com.example.demo.Model.Url;
import com.example.demo.Model.UrlDTO;
import com.example.demo.Repository.UrlRepository;

import com.google.common.hash.Hashing;

import io.micrometer.common.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/* Componente es unidad modular e independiente que desempeña una función específica dentro de un sistema. 
   encapsula  parte de la funcionalidad de la aplicación; es reutilizable, intercambiable y extensible. */


@Component
public class UrlServiceIMP implements UrlService { // Uso esta clase para implementar los metodos ya definidos.

    //private static final Logger logger = LoggerFactory.getLogger(UrlServiceIMP.class);

    @Autowired //Spring buscará un bean que coincida con el tipo de la variable y lo inyectará automáticamente. 
    private UrlRepository DB;
    // Esto permite que los componentes de una aplicación se conecten entre sí, sin instanciar las dependencias explicitamente.



    // Funciones Auxiliares

    // MurmurHash is a non-cryptographic hash function, which yields a 32-bit or 128-bit hash value.
    private String encodeUrl(String url) {
        // Para evitar colisiones puedo usar la fecha actual o el id acumulativo de cada Url.
        // id: Long (64bits) -> 9'223,372,036,854,775,807 Url unicas. (base 62 alfanumerica)
        String codigo = ""; LocalDateTime t = LocalDateTime.now();

        codigo = Hashing.murmur3_32_fixed() //Metodo de Google-Guava
                .hashString( url + t.toString() , StandardCharsets.UTF_8).toString();

        return  codigo;
    }

    private LocalDateTime deffExpiration(String expirationDate, LocalDateTime creationDate)
    {
        if( StringUtils.isBlank(expirationDate) ){ return creationDate.plusHours(72); }
        // Si no se define una exp.date, se definen 3d por defecto.

        LocalDateTime expString = LocalDateTime.parse(expirationDate); // String -> LocalDateTime
        return expString;
    }


    // Funciones definidas en la Clase de SERVICIO (UrlService)
    @Override
    public Url generateShortLink(UrlDTO UrlDTO) { //UrlDTO -> OriginalUrl

        if( StringUtils.isNotBlank(UrlDTO.getUrl()) ) //Evalúa si es un whitespace, es nulo o está vacío ("").
        {
            String codigo = encodeUrl(UrlDTO.getUrl()); // Primero saco el codigo a asociar.

            Url url = new Url();
            url.setCreationDate(LocalDateTime.now());  
            url.setExpirationDate( deffExpiration( UrlDTO.getExpirationDate(), url.getCreationDate() ) );
            url.setOriginalUrl(UrlDTO.getUrl()); 
            url.setShortLink(codigo); // Luego defino la fila a insertar en la Base
            
            Url responseUrl = saveinDB(url); // Por ultimo envio la Url a la DB.

            return responseUrl;  // Recibo la Url completa con todo todo
        }

        return null;
    }


    @Override
    public Url saveinDB(Url url) {
        Url sendUrl = DB.save(url); // Hereda de JpaRepository<Url,Long>
        return sendUrl;
    } // save() guarda o actualiza una entidad en la DB. Retorna la entidad + id creado. 

    @Override
    public Url findOriginal(String codigo) {
        Url responseUrl = DB.findByShortLink(codigo);
        return responseUrl; //El codigo es unico y la respuesta tambien.
    }

    @Override 
    public void deleteShortLink(Url url) { DB.delete(url); } // Hereda de JpaRepository<Url,Long>

}