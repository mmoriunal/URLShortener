package com.example.demo.Service;

import com.example.demo.DTO.UsuarioRegistroDTO;
import com.example.demo.Model.Role;
import com.example.demo.Model.Usuario;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UsuarioRepository;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioServiceIMP implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UsuarioServiceIMP(UsuarioRepository usuarioRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUsuario(UsuarioRegistroDTO registroDTO){
        Usuario usuario = new Usuario();
        usuario.setUsername(registroDTO.getUsername());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

        Role role = roleRepository.findByUsername("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        usuario.setRoles(Arrays.asList(role));
        usuarioRepository.save(usuario);
    }

    @Override
    public Usuario findUsuarioByUsername(String usuario) {
        return usuarioRepository.findByUsername(usuario);
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}