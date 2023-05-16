package com.shortener.URLShortener;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; //Request Mapping to redirect to index.HTML file (HTML Template - FrontEnd)

@Controller
public class UrlController {
	
	// Implementar REQUESTMAPPING  -   Default PATH:  value = '/'
	@RequestMapping(value="/", method=RequestMethod.GET)  //SpringBoot Method
	public String loadIndex() {
		return "index";
	}
}
