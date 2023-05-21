package com.shortener.URLShortener;

import org.springframework.stereotype.Controller; // @Controller
import org.springframework.web.bind.annotation.RequestMapping; // @RequestMapping
import org.springframework.web.bind.annotation.RequestMethod; //RequestMethod to redirect to index.HTML file (HTML Template - FrontEnd)

@Controller
public class UrlController {
	
	/* Implementar REQUESTMAPPING  -   Default PATH:  value = '/' */
	@RequestMapping(value="/", method=RequestMethod.GET)  //SpringBoot Method
	public String loadIndex() {
		return "index"; // Retorna el nombre de una vista (HTML template) a mostrar
	}
}
