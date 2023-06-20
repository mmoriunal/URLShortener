package com.example.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Model.UserDTO;
import com.example.demo.Service.EmailSenderService;
import com.example.demo.Service.UserService;

@Controller
@RequestMapping("/signup")
public class SignUpController {

	@Autowired
	private UserService userService;
	@Autowired
	private EmailSenderService emailSender;
	
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
		String pswd = userDTO.getPassword();

		if( userService.nombreDuplicado(nombre) ){
			return "redirect:/signup?sign0";
		}
		if( userService.emailDuplicado(email) ){
			return "redirect:/signup?sign00";
		}
		if( pswd.length() < 8 ){
			return "redirect:/signup?sign000";
		}

		userService.saveUser(userDTO);
		emailSender.sendEmailBienvenida(email, nombre);
		return "redirect:/signup?sign1"; 
	}

}
