package com.example.demo.Service;
import com.example.demo.Model.Url;
import com.example.demo.Model.UrlDTO;
import com.example.demo.Repository.UrlRepository;

import com.google.common.hash.Hashing;


import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
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

    @Override
    public LocalDateTime deffExpiration(String exp){ // Se definen 3d por defecto.
        LocalDateTime creationDate = LocalDateTime.now();
        if( StringUtils.isBlank(exp) ){ return creationDate.plusDays(3); }
        
        try{
            int e = Integer.parseInt(exp);
            return creationDate.plusDays(e);
        }catch( Exception e ){return creationDate.plusDays(3);}
    }


    // Funciones definidas en la Clase de SERVICIO (UrlService)
    @Override
    public Url generateShortLink(UrlDTO UrlDTO, String userLink) { //UrlDTO -> OriginalUrl

        if( StringUtils.isNotBlank(UrlDTO.getUrl()) ) { //Evalúa si es un whitespace, es nulo o está vacío ("").

            String codigo = encodeUrl(UrlDTO.getUrl()); // Primero saco el codigo a asociar.

            Url url = new Url();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime hoy = LocalDateTime.now(), exp = deffExpiration( UrlDTO.getExp() );
            String str_hoy = hoy.format(formatter), str_exp = exp.format(formatter);

            url.setUserLink(userLink);
            url.setCreationDate(str_hoy);  
            url.setExpirationDate(str_exp);
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

    @Override
    public ArrayList<Url> listaUrl(String current){
        ArrayList<Url> links = DB.findAllByUserLink(current);
        System.out.println(links);
        return links;
    }

    @Override
    public ArrayList<Url> linksDuplicados(String original){
        return DB.findAllByOriginalUrl(original);
    }

    @Override
    public boolean hayDuplicado(String original){
        return DB.existsByOriginalUrl(original);
    }

}