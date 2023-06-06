package com.example.demo.Controller;
import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.Model.ErrorDTO;
import com.example.demo.Model.Url;
import com.example.demo.Service.UrlService;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;

/* @RestController devolveria directamente objetos que son respuestas HTTP (JSON)
 * Hace mas facil los servicios web RESTful al eliminar la necesidad de anotar cada método con @ResponseBody.
 * Un controlador mixto (JSON - Vistas) requeire ser anotado ya con @Controller. */


@Controller
public class UrlController {
    @Autowired // Una forma facil de acceder a los metodos de otras clases en escencia.
    private UrlService UrlService;
    

    
    // Recibir UrlOriginal a partir de ShortUrl; redirigir al link original.

    @GetMapping("/{codigo}") // Metodo activo cuando se realice una solicitud GET a las muchas rutas posibles
    public String redirect( @PathVariable String codigo, HttpServletResponse enviador, Model model ) throws IOException{
        // HttpServletResponse interfaz para las respuestas enviadas de un Servidor a un Cliente.
        // sendRedirect(String location): Redirige al cliente a otra URL especificada.

        Url response = UrlService.findOriginal(codigo); 
        LocalDateTime hoy = LocalDateTime.now();

        ErrorDTO error = new ErrorDTO(); error.setStatus("400");
        Boolean flag = false;
        
        if ( StringUtils.isBlank(codigo) ){ 
            error.setError("Este link esta vacio. Porfavor ingrese uno valido :D");
            flag = true;
        }
        else if( response.getExpirationDate().isBefore(hoy) ){
            error.setError("Link caducado. Porfavor cree uno nuevo :D");
            UrlService.deleteShortLink(response); //Eliminar link caducado

            flag = true;
        }
        if( response == null ){ //En caso el usuario insista.
            error.setError("Link inexistente. Porfavor cree uno nuevo :D");
            flag = true;
        }
        if(flag){
            model.addAttribute("estado", error.getStatus());
            model.addAttribute("msg", error.getError());

            return "error";
        }

        try{ enviador.sendRedirect( response.getOriginalUrl() ); }
        catch(Exception e){ 
            model.addAttribute("estado", "404");
            model.addAttribute("msg", "La dirección no se ha encontrado. Porfavor verifique que el enlace sea valido :)");

            return "error";
        }
        
        return null; 
    }
}
