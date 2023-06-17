package com.example.demo.Service;

import com.example.demo.DTO.UsuarioRegistroDTO;
import com.example.demo.Model.Usuario;

public interface UsuarioService {

    public void saveUsuario(UsuarioRegistroDTO registroDTO);

    public Usuario findUsuarioByUsername(String Username);

    //public List<URL> findAllURLs(); 
}