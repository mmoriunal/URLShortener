package com.example.demo.Service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.Model.UserDTO;
import com.example.demo.Model.Usuario;


public interface UserService extends UserDetailsService{
	public Usuario saveUser(UserDTO registroDTO);
	public List<Usuario> listarUsuarios();
	public String getCurrentUser();
	public boolean nombreDuplicado(String nombre);
	public boolean emailDuplicado(String email);
}

/*
 * La implementación de la interfaz UserDetailsService se utiliza para cargar los detalles de usuario durante la autenticación,
 * cuando un usuario intenta iniciar sesión en una aplicación. Spring Security utiliza estos detalles para verificar los Roles del usuario
 * y determinar sus autorizaciones y permisos; realizando consultas a una Base de Datos. 
 */