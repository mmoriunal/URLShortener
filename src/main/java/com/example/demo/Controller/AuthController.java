package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.DTO.UsuarioRegistroDTO;
import com.example.demo.Model.Usuario;
import com.example.demo.Service.UsuarioService;

import jakarta.validation.Valid;

@Controller
public class AuthController {

    private UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @GetMapping("/ezlink")
    public String home(){
        return "index";
    }

   
    @GetMapping("/registro")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UsuarioRegistroDTO usuario = new UsuarioRegistroDTO();
        model.addAttribute("user", usuario);
        return "registro";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UsuarioRegistroDTO usuarioDto,
                               BindingResult result,
                               Model model){
        Usuario existingUser = usuarioService.findUsuarioByUsername(usuarioDto.getUsername());

        if(existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", usuarioDto);
            return "/registro";
        }

        usuarioService.saveUsuario(usuarioDto);
        return "redirect:/registro?success";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
