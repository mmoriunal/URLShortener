package com.shortener.URLShortener;

import java.util.HashMap;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // Respuesta HTTP que se enviará al cliente desde un controlador.

import java.io.IOException;
import java.net.MalformedURLException; // Error en caso la url este incorrecta y no arroje algún vinculo 

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody; // Importa @RequestBody
import org.springframework.web.bind.annotation.RequestMapping; // Importa @RequestMapping
import org.springframework.web.bind.annotation.RestController; // Importa @RestController
import org.springframework.web.bind.annotation.RequestMethod; // RequestMethod.POST (enviar información al servidor) / .GET (recibir)

import jakarta.servlet.http.HttpServletResponse;


@RestController
public class UrlRestController {
    
    private HashMap<String, ShortUrl> shorturlList = new HashMap<>();
    private void crearUrl(String r, ShortUrl shortenUrl){
        shortenUrl.setShort("http://localhost:8080/s/" + r);
        shorturlList.put(r, shortenUrl);
   }

    /*  
    Controlador que maneja las solicitudes POST a la URL /shortenurl.
    Recibe un objeto ShortUrl en el cuerpo de la solicitud,
    genera un carácter aleatorio para la URL acortada
    y establece la URL acortada asociada (r) para la shorturl entrante.
    Luego, devuelve un objeto ResponseEntity que contiene 'r' como respuesta HTTP.
    */
	@RequestMapping(value="/shortenurl", method = RequestMethod.POST) // Mapear una URL específica (/shortenurl) a un método getShortUrl(). 
    // Recibe un objeto ShortUrl como parámetro, que se obtiene del cuerpo de la solicitud utilizando la anotación @RequestBody. 
	public ResponseEntity<Object>  getShortUrl(@RequestBody ShortUrl shorturl) throws MalformedURLException {
		String r = randChar();
		crearUrl(r, shorturl); // Establece la URL acortada asociada (r) para el shorturl recibido.

		return new ResponseEntity<Object>(shorturl, HttpStatus.OK); // HttpStatus.OK para indicar que la solicitud se ha procesado correctamente.
	}

	@RequestMapping(value="/s/{randomstring}", method=RequestMethod.GET)
	public void getFullUrl(HttpServletResponse response, @PathVariable("randomstring") String randomString) throws IOException{
		response.sendRedirect(shorturlList.get(randomString).getFull());
	}

 
    // Generador de cadenas alfanumericas aleatorias.
	private String randChar() {
		String str = ""; Random r = new Random();
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		for (int i = 0; i < 5; i++)
			str += chars.charAt( r.nextInt(str.length()) );
		return str;
	}

}
