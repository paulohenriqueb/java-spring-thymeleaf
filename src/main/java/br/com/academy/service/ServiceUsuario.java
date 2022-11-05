package br.com.academy.service;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academy.DAO.UsuarioDAO;
import br.com.academy.Exceptions.CriptoExistException;
import br.com.academy.Exceptions.EmailExistsException;
import br.com.academy.Exceptions.ServiceExc;
import br.com.academy.model.Usuario;
import br.com.academy.util.Util;

@Service
public class ServiceUsuario {
	@Autowired
	private UsuarioDAO usuarioDaoResitorio;
	
	private static HttpSession sessionHTTP;
	public void salvarUsuario(Usuario usuario) throws Exception{
		try {
			if(usuarioDaoResitorio.findByEmail(usuario.getEmail()) != null) {
				throw new EmailExistsException("O usuário ou email já existe");
			}
			usuario.setSenha(Util.md5(usuario.getSenha()));
		}catch(NoSuchAlgorithmException e) {
			throw new CriptoExistException("Erro durante a criptografia da senha");
		}
		usuarioDaoResitorio.save(usuario);
	}
	
	public Usuario loginUser(String email, String senha) throws ServiceExc{
		Usuario usuarioLogin = usuarioDaoResitorio.buscarLogin(email, senha);
		return usuarioLogin;
	}
	
	public static HttpSession getSessionHTTP() {
		return sessionHTTP;
	}

	public static void setSessionHTTP(HttpSession httpSession) {
		sessionHTTP = httpSession;
	}
}
