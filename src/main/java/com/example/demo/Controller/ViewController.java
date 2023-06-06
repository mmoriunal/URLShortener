package com.example.demo.Controller;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Model.ErrorDTO;
import com.example.demo.Model.ResponseDTO;
import com.example.demo.Model.Url;
import com.example.demo.Model.UrlDTO;
import com.example.demo.Service.UrlService;

//Tambien necesito devolver vistas, entonces tengo que usar @Controller que es general.

@Controller
public class ViewController {
    @Autowired // Una forma facil de acceder a los metodos de otras clases en escencia.
    private UrlService UrlService;

    
    @GetMapping("/ezlink")
    public String index(){ return "index"; } 

    // Enviar UrlOriginal y producir ShortUrl
    @PostMapping("/register") 
    public String generateShortLink(@ModelAttribute UrlDTO UrlDTO, Model model){

        ErrorDTO error = new ErrorDTO();
        if (StringUtils.isBlank(UrlDTO.getUrl()) ){ 
            error.setError("Porfavor ingrese una URL :)");
            error.setStatus("HTTP 404");

            model.addAttribute("short", error.getError());
            return "index";
        }
        if ( ! UrlDTO.getUrl().contains(".com") ){ 
            error.setError("Porfavor ingrese una URL valida :)");
            error.setStatus("HTTP 404");

            model.addAttribute("short", error.getError());
            return "index";
        }

        //No uso @Autowired para UrlDTO porque necesito una instancia per se para recibir como respuesta.
        Url dbUrl = UrlService.generateShortLink(UrlDTO); 
        String original = dbUrl.getOriginalUrl();
        String shortUrl = "http://localhost:8080/" + dbUrl.getShortLink();
        LocalDateTime exp = dbUrl.getExpirationDate();

        ResponseDTO response = new ResponseDTO(original, shortUrl, exp); 
        model.addAttribute("short", response.getShortLink());

        return "index";
    }
}
