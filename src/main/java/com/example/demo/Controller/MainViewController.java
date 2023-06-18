package com.example.demo.Controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.DTO.ErrorDTO;
import com.example.demo.DTO.ResponseDTO;
import com.example.demo.DTO.UrlDTO;
import com.example.demo.DTO.UsuarioRegistroDTO;
import com.example.demo.Model.Url;
import com.example.demo.Model.Usuario;
import com.example.demo.Service.UrlService;
import com.example.demo.Service.UsuarioService;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;

@Controller
public class MainViewController {

    private UsuarioService usuarioService;
    private UrlService urlService;

    public MainViewController(UsuarioService usuarioService, UrlService urlService) {
        this.usuarioService = usuarioService;
        this.urlService = urlService;
    }

    @GetMapping("/ezlink")
    public String home(){
        return "index";
    }

    @GetMapping("/registro")
    public String showRegistrationForm(Model model){
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

    @PostMapping("/ezlink")
    public String generateShortLink(@ModelAttribute UrlDTO urlDTO, Model model){

        ErrorDTO error = new ErrorDTO();
        if (StringUtils.isBlank(urlDTO.getUrl())) {
            error.setError("Porfavor ingrese una URL :)");
            error.setStatus("HTTP 404");

            model.addAttribute("short", error.getError());
            return "index";
        }
        if (!urlDTO.getUrl().contains(".com")) {
            error.setError("Porfavor ingrese una URL valida :)");
            error.setStatus("HTTP 404");

            model.addAttribute("short", error.getError());
            return "index";
        }

        Url dbUrl = urlService.generateShortLink(urlDTO);
        String original = dbUrl.getOriginalUrl();
        String shortUrl = "http://localhost:8080/" + dbUrl.getShortLink();
        LocalDateTime exp = dbUrl.getExpirationDate();

        ResponseDTO response = new ResponseDTO(original, shortUrl, exp);
        model.addAttribute("short", response.getShortLink());

        return "index";
    }
}

