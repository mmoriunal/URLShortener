package com.example.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Model.UserDTO;
import com.example.demo.Service.UserService;

@Controller
@RequestMapping("/signup")
public class SignUpController {

	@Autowired
	private UserService userService;
	
	@ModelAttribute("usuario")
	public UserDTO newUserDTO() {
		return new UserDTO();
	}

	@GetMapping
	public String signUp() {
		return "signup";
	}
	
	@PostMapping
	public String register(@ModelAttribute("usuario") UserDTO userDTO) {
		String nombre = userDTO.getNombre(), email = userDTO.getEmail();

		if( userService.nombreDuplicado(nombre) ){
			return "redirect:/signup?sign0";
		}
		if( userService.emailDuplicado(email) ){
			return "redirect:/signup?sign00";
		}

		userService.saveUser(userDTO);
		return "redirect:/signup?sign1"; 
	}

}
