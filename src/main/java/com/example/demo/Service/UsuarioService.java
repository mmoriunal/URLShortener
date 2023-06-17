package com.example.demo.Service;

import com.example.demo.Model.Usuario;
import com.example.demo.Model.UsuarioRegistroDTO;

public interface UsuarioService {

    public Usuario saveinDB(UsuarioRegistroDTO registroDTO);
}