package com.example.demo.Service;
import com.example.demo.Model.Url;
import com.example.demo.Model.UrlDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;


@Service
public interface UrlService { // Usare esta clase para definir todos los metodos del Modelo
    public LocalDateTime deffExpiration(String exp);
    public Url generateShortLink(UrlDTO urlDTO, String userLink);

    public Url saveinDB(Url url);
    public Url findOriginal(String url);
    public  void  deleteShortLink(Url url);
    public ArrayList<Url> listaUrl(String current);

    public boolean hayDuplicado(String original);
    public ArrayList<Url> linksDuplicados(String original);
}

/* Cuando una clase es considerada un componente de servicio, generalmente significa que cumple un rol específico
 en la lógica de negocio de una aplicación y ofrece funcionalidades relacionadas a un servicio específico.

 Brinda una abstracción de bajo nivel. */