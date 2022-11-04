package br.com.academy.controllers;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


import br.com.academy.Exceptions.ServiceExc;
import br.com.academy.model.Aluno;
import br.com.academy.model.Usuario;
import br.com.academy.service.ServiceUsuario;
import br.com.academy.util.Util;

@Controller
public class UsuarioController {
	
	@Autowired
	private ServiceUsuario serviceUsuario;
	
	@GetMapping(value = {"/", "login"})
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		System.out.println(serviceUsuario.getSessionHTTP());
		
		modelAndView.setViewName(checkLogin("redirect:/index"));
		
		modelAndView.addObject("usuario", new Usuario());
		return modelAndView;
	}
	
	@GetMapping("/cadastro")
	public ModelAndView cadastrar() {
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName(checkLogin("login/cadastro"));
		
		modelAndView.addObject("usuario", new Usuario());
		return modelAndView;
	}
	
	@PostMapping("salvar")
	public ModelAndView cadastrar(Usuario usuario) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		serviceUsuario.salvarUsuario(usuario);
		modelAndView.setViewName("redirect:/login");
		return modelAndView;
	}
	
	@PostMapping("login")
	public ModelAndView loginUsuario(@Valid Usuario usuario, BindingResult bindingResult, HttpSession session) throws NoSuchAlgorithmException, ServiceExc{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("usuario", new Usuario());
		if(bindingResult.hasErrors()) {
			modelAndView.setViewName("login/login");
		}
		Usuario usuarioLogin = serviceUsuario.loginUser(usuario.getEmail(), Util.md5(usuario.getSenha()));
		if(usuarioLogin == null) {
			modelAndView.addObject("msg", "Usuario não encontrado tente novamente");
		}else {
			session.setAttribute("usuarioLogado", usuarioLogin);
			serviceUsuario.setSessionHTTP(session);
			return index(serviceUsuario.getSessionHTTP());
		}
		return modelAndView;
	}
	
	@GetMapping(value = "index")
	public ModelAndView index(HttpSession httpSession) {
		ModelAndView modelAndView = new ModelAndView();
		if(httpSession.getAttribute("usuarioLogado") == null) {
			modelAndView.setViewName("redirect:/login");
		}else {
			modelAndView.setViewName("home/index");
		}
		modelAndView.addObject("aluno", new Aluno());
		return modelAndView;
	}
	
	@PostMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		session.invalidate();
		serviceUsuario.setSessionHTTP(null);
		return login();
	}
	
	public String checkLogin(String parametro) {
		if(serviceUsuario.getSessionHTTP() != null) {
			return parametro;
		}
		return "login/login";
	}
	
}
