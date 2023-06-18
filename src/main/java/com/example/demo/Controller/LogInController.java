package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LogInController {
    @GetMapping
	public String logIn() {
		return "login";
	}
}

/*
 * De la administracion de tipo POST, se encarga UserDetailsService;
 * que es la clase padre de UserService. 
 */
