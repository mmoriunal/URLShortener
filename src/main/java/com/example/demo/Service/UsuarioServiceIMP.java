package com.example.demo.Service;

import com.example.demo.Model.Usuario;
import com.example.demo.Model.UsuarioRegistroDTO;
import com.example.demo.Repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UsuarioServiceIMP implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario saveinDB(UsuarioRegistroDTO registroDTO){
        Usuario usuario = new Usuario(registroDTO.getUsername(), registroDTO.getPassword());
        return usuarioRepository.save(usuario);
    }
}