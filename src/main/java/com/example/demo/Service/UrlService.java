package com.example.demo.Service;
import com.example.demo.DTO.UrlDTO;
import com.example.demo.Model.Url;

import org.springframework.stereotype.Service;


@Service
public interface UrlService { // Usare esta clase para definir todos los metodos del Modelo
    public Url generateShortLink(UrlDTO urlDTO);

    public Url saveinDB(Url url);
    public Url findOriginal(String url);
    public  void  deleteShortLink(Url url);
}

/* Cuando una clase es considerada un componente de servicio, generalmente significa que cumple un rol específico
 en la lógica de negocio de una aplicación y ofrece funcionalidades relacionadas a un servicio específico.

 Brinda una abstracción de bajo nivel. */