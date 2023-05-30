package com.example.demo.Controller;
import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.ErrorDTO;
import com.example.demo.Model.ResponseDTO;
import com.example.demo.Model.Url;
import com.example.demo.Model.UrlDTO;
import com.example.demo.Service.UrlService;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;

/* Esta clase devolvera directamente objetos que son respuestas HTTP (JSON)
 * Hace mas facil los servicios web RESTful al eliminar la necesidad de anotar cada método con @ResponseBody. */


@RestController
public class UrlController {
    @Autowired // Una forma facil de acceder a los metodos de otras clases en escencia.
    private UrlService urlService;
    

    // Enviar UrlOriginal y producir ShortUrl

    @PostMapping("/ezlink") // Se define la ruta especifica cuyas Solicitudes POST seran manejadas por el metodo
    public ResponseEntity<?> generateShortLink(@RequestBody UrlDTO urlDTO){
    // @RequestBody indica que el objeto UrlDTO sera vinculado como parametro al cuerpo de la solicitud HTTP que estoy enviando. 

        if (StringUtils.isBlank(urlDTO.getUrl()) ){ 
            ErrorDTO error = new ErrorDTO();
            error.setError("Algo no funciona. Porfavor verifique que la URL sea valida :)");
            error.setStatus("HTTP 404 Not Found");
            return new ResponseEntity<ErrorDTO>(error, HttpStatus.OK);
        }

        //No uso @Autowired para UrlDTO porque necesito una instancia per se para recibir como respuesta.
        Url responseUrl = urlService.generateShortLink(urlDTO); 
        String title = responseUrl.getTitle();    
        String original = responseUrl.getOriginalUrl();
        String shortUrl = "http://localhost:8080/" + responseUrl.getShortLink();
        LocalDateTime exp = responseUrl.getExpirationDate();

        ResponseDTO response = new ResponseDTO(title, original, shortUrl, exp); 
        // HttpStatus.OK indica si la solicitud se procesó correctamente
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK); 
    }


    // Recibir UrlOriginal a partir de ShortUrl; redirigir al link original.

    @GetMapping("/{codigo}") // Metodo activo cuando se realice una solicitud GET a las muchas rutas posibles
    public ResponseEntity<?> redirect( @PathVariable String codigo, HttpServletResponse enviador ) throws IOException{
        // HttpServletResponse interfaz para las respuestas enviadas de un Servidor a un Cliente.
        // sendRedirect(String location): Redirige al cliente a otra URL especificada.

        Url response = urlService.findOriginal(codigo); LocalDateTime hoy = LocalDateTime.now();
        ErrorDTO error = new ErrorDTO(); String status = "HTTP 400 Bad Request";
        

        if ( StringUtils.isBlank(codigo) ){ 
            error.setError("Este link esta vacio. Porfavor ingrese uno valido :)");
            error.setStatus(status);
            return new ResponseEntity<ErrorDTO>(error, HttpStatus.OK);
        }

        else if( response.getExpirationDate().isBefore(hoy) ){
            error.setError("Link caducado. Porfavor cree uno nuevo :)");
            error.setStatus(status);

            urlService.deleteShortLink(response); //Eliminar link caducado

            return new ResponseEntity<ErrorDTO>(error, HttpStatus.OK);
        }

        else if( response == null ){ //En caso el usuario insista.
            error.setError("Link inexistente. Porfavor cree uno nuevo :)");
            error.setStatus(status); 

            return new ResponseEntity<ErrorDTO>(error, HttpStatus.OK);
        }

        try{ enviador.sendRedirect( response.getOriginalUrl() ); }
        catch(Exception e){ 
            return new ResponseEntity<String>("Algo salio mal... Porfavor intente de nuevo.", HttpStatus.OK); 
        }
        
        return null; //This method must return a result of type ResponseEntity<?> (de alguna forma lo arregla)
    }
}
