package br.com.academy.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.academy.model.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, Long> {
	@Query("SELECT u FROM Usuario u WHERE u.email = :email ")
	public Usuario findByEmail(String email);
	
	@Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha")
	public Usuario buscarLogin(String email, String senha);
}
