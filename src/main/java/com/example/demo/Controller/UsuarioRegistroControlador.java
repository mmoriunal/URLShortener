package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Model.UsuarioRegistroDTO;
import com.example.demo.Service.UsuarioService;

@Controller
@RequestMapping("/signin")
public class UsuarioRegistroControlador {
    
    @Autowired
    private UsuarioService usuarioService;

    @ModelAttribute("usuario")
    public UsuarioRegistroDTO nuevoUsuarioRegistroDTO(){
        return new UsuarioRegistroDTO();
    }

    @GetMapping
    public String mostrarSignIn(){
        return "signin";
    }

    @PostMapping
    public String registrarUsuario(@ModelAttribute("usuario") UsuarioRegistroDTO registroDTO){
        usuarioService.saveinDB(registroDTO);
        return "redirect:/signin?exito";
    }
}
