package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.Service.UsuarioService;

@Controller
public class UsuarioLoginControlador {

	@Autowired
	private UsuarioService servicio;
	
	@GetMapping("/login")
	public String iniciarSesion() {
		return "login";
	}
}
