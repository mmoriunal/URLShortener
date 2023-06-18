package com.example.demo.Controller;
import com.example.demo.Model.ErrorDTO;
import com.example.demo.Model.ResponseDTO;
import com.example.demo.Model.Url;
import com.example.demo.Model.UrlDTO;
import com.example.demo.Service.UrlService;
import com.example.demo.Service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/")
public class IndexContoller {

	@Autowired
	private UserService userService;
	@Autowired
	private UrlService urlService;

	@ModelAttribute("urldto")
	public UrlDTO newUrlDTO() { return new UrlDTO(); }
	
	@GetMapping
	public String home(Model model) {
        String current = userService.getCurrentUser();
        model.addAttribute("links", urlService.listaUrl(current)); //Listar todas las URL creadas por el usuario actual

		return "index";
	}

    // Enviar UrlOriginal y producir ShortUrl
    @PostMapping
    public String generateShortLink(@ModelAttribute("urldto") UrlDTO UrlDTO, Model model){

        String current = userService.getCurrentUser();
        model.addAttribute("links", urlService.listaUrl(current));

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


        if( urlService.hayDuplicado(UrlDTO.getUrl()) ){
            ArrayList<Url> duplicados = urlService.linksDuplicados(UrlDTO.getUrl());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDateTime tentativa = urlService.deffExpiration(UrlDTO.getExp());
            String str_exp1 = tentativa.format(formatter);
            for( Url duplicado : duplicados ){

                String str_exp2 = duplicado.getExpirationDate();
                String user_asociado = duplicado.getUserLink();
                if( str_exp1.equals(str_exp2) && current.equals(user_asociado) ){
                    error.setError("URL y Fecha duplicadas, por favor ingrese una nueva :)");
                    model.addAttribute("short", error.getError());

                    return "index";
                }   
            }
        }


        //No uso @Autowired para UrlDTO porque necesito una instancia per se para recibir como respuesta.
        Url dbUrl = urlService.generateShortLink(UrlDTO, current); 
        String original = dbUrl.getOriginalUrl();
        String shortUrl = "http://localhost:8080/" + dbUrl.getShortLink();
        String exp = dbUrl.getExpirationDate();

        ResponseDTO response = new ResponseDTO(original, shortUrl, exp); 
        model.addAttribute("short", response.getShortLink());

        model.addAttribute("links", urlService.listaUrl(current)); //Actualizar tabla inmediatamente si hubo creacion exitosa

        return "index";
    }
}


/*
 * Puedes acceder al usuario autenticado utilizando la clase SecurityContextHolder y el método getContext().
 * A partir de ahí, puedes obtener los detalles del usuario autenticado utilizando el método getAuthentication(),
 * y finalmente obtener el objeto PRINCIPAL que representa al usuario autenticado.
 */