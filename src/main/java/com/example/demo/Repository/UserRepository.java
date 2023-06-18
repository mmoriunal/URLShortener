package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long>{
	public Usuario findByNombre(String nombre);
	public boolean existsByNombre(String nombre);
	public boolean existsByEmail(String email);
}
