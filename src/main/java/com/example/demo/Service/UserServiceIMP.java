package com.example.demo.Service;
import com.example.demo.Model.Rol;
import com.example.demo.Model.UserDTO;
import com.example.demo.Model.Usuario;
import com.example.demo.Repository.UserRepository;


import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;


@Service
public class UserServiceIMP implements UserService {

	@Autowired
	private UserRepository DB;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

	@Override
	public Usuario saveUser(UserDTO registroDTO) throws PersistenceException {
		try{
			Usuario usuario = new Usuario(	registroDTO.getNombre(), 
											registroDTO.getEmail(),
											passwordEncoder.encode(registroDTO.getPassword()),
											Arrays.asList(new Rol("cliente"))
											);
			return DB.save(usuario); }
		catch (PersistenceException e){
			Throwable cause = e.getCause();
			if( cause instanceof ConstraintViolationException ){ throw (PersistenceException) cause ;}
		}

		System.out.println("Hubo otro error en la creación del Usuario.");
		return null;
	}

	/* 
	Implementación del método loadUserByUsername de la interfaz UserDetailsService en Spring
	Se utiliza para cargar los detalles del usuario por su nombre de usuario.
	Puede lanzar una excepción UsernameNotFoundException si el usuario no existe.
	*/
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //POST Method para LogIn
		Usuario usuario = DB.findByNombre(username);
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario o contraseña inválidos");
		}
		//	User: principal; Arg1: username; Arg2: password; Arg3: Role Authorization
		return new User(usuario.getNombre(),usuario.getPassword(), mapearAutoridadesRoles(usuario.getRoles()));
		/* Se crea un nuevo objeto User, util para mapear los roles a una colección de objetos GrantedAuthority.  */
	}

	private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
	}

	
	/* Puedo acceder al usuario autenticado utilizando la clase SecurityContextHolder y el método getContext().
	* A partir de ahí, obtener los detalles del usuario autenticado utilizando el método getAuthentication(),
	* y finalmente obtener el objeto PRINCIPAL que representa al usuario autenticado. */
	@Override
	public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println(userDetails.getUsername());
            return userDetails.getUsername();
		}
		return null;
	}


	@Override
	public List<Usuario> listarUsuarios() {
		return DB.findAll();
	}

	@Override
	public boolean nombreDuplicado(String nombre) {
		return DB.existsByNombre(nombre);
	}

	@Override
	public boolean emailDuplicado(String email) {
		return DB.existsByEmail(email);
	}

}
