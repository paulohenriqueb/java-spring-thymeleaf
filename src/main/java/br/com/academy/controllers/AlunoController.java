package br.com.academy.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.academy.DAO.AlunoDAO;
import br.com.academy.model.Aluno;
import br.com.academy.model.Usuario;
import br.com.academy.service.ServiceUsuario;

@Controller
public class AlunoController {
	
	@Autowired
	private AlunoDAO alunoDaoRepositorio;
		
	@GetMapping("/criar-alunos")
	public ModelAndView insertAlunos(Aluno aluno) {
		ModelAndView modelAndView = new ModelAndView();
		if(ServiceUsuario.getSessionHTTP() == null) {
			modelAndView.addObject("usuario", new Usuario());
		}
		modelAndView.setViewName(UsuarioController.checkLogin("aluno/formAluno"));
		modelAndView.addObject("aluno", new Aluno());
		return modelAndView;
	}

	@PostMapping("insertAlunos")
	public ModelAndView inserirAluno(@Valid Aluno aluno, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		if(bindingResult.hasErrors()) {
			modelAndView.setViewName("aluno/formAluno");
			modelAndView.addObject("aluno");
		}else {
			modelAndView.setViewName("redirect:/alunos");
			alunoDaoRepositorio.save(aluno);
		}
		
		return modelAndView;
	}
	
	@GetMapping("/alunos")
	public ModelAndView listAlunos() {
		ModelAndView modelAndView = new ModelAndView();
		if(ServiceUsuario.getSessionHTTP() == null) {
			modelAndView.addObject("usuario", new Usuario());
		}
		modelAndView.setViewName(UsuarioController.checkLogin("aluno/alunos"));
		modelAndView.addObject("alunosList", alunoDaoRepositorio.findAll());
		return modelAndView;
	}
	
	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Integer id) {
		ModelAndView modelAndView = new ModelAndView();
		if(ServiceUsuario.getSessionHTTP() == null) {
			modelAndView.addObject("usuario", new Usuario());
		}
		modelAndView.setViewName(UsuarioController.checkLogin("aluno/formAluno"));
		Aluno aluno = alunoDaoRepositorio.getReferenceById(id);
		modelAndView.addObject("aluno", aluno);
		return modelAndView;
	}
	
	@PostMapping("/editar")
	public ModelAndView editar(Aluno aluno) {
		ModelAndView modelAndView = new ModelAndView();
		alunoDaoRepositorio.save(aluno);
		modelAndView.setViewName("redirect:/alunos");
		return modelAndView;
	}
	
	@GetMapping("excluir/{id}")
	public String excluir(@PathVariable("id") Integer id) {
		if(ServiceUsuario.getSessionHTTP() == null) {
			return "redirect:/login";
		}
		alunoDaoRepositorio.deleteById(id);
		return "redirect:/alunos";
	}
	
	@GetMapping("filtro-alunos")
	public ModelAndView filtrosAlunos() {
		ModelAndView modelAndView = new ModelAndView();
		if(ServiceUsuario.getSessionHTTP() == null) {
			modelAndView.addObject("usuario", new Usuario());
		}
		modelAndView.setViewName(UsuarioController.checkLogin("aluno/filtro-aluno"));
		modelAndView.addObject("aluno", new Aluno());
		return modelAndView;
	}
	
	@GetMapping("status/{status}")
	public ModelAndView alunosStatus(@PathVariable("status") String status) {
		ModelAndView modelAndView = new ModelAndView();
		if(ServiceUsuario.getSessionHTTP() == null) {
			modelAndView.addObject("usuario", new Usuario());
		}
		modelAndView.setViewName(UsuarioController.checkLogin("aluno/alunos"));
		switch (status.toLowerCase()) {
			case "ativo": 
				modelAndView.addObject("alunosList", alunoDaoRepositorio.findStatusAtivo());
				break;
			case "inativo":
				modelAndView.addObject("alunosList", alunoDaoRepositorio.findStatusInativo());
				break;
			case "trancado":
				modelAndView.addObject("alunosList", alunoDaoRepositorio.findStatusTrancado());
				break;
			case "cancelado":
				modelAndView.addObject("alunosList", alunoDaoRepositorio.findStatusCancelado());
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + status);
		}
		
		return modelAndView;
	}
	
	@PostMapping("pesquisar-aluno")
	public ModelAndView pesquisarAluno(@RequestParam(required = false) String nome) {
		ModelAndView modelAndView = new ModelAndView();
		List<Aluno> listAlunos;
		System.out.println(nome);
		if(nome == null || nome.trim().isEmpty()) {
			listAlunos = alunoDaoRepositorio.findAll();
		}else {
			listAlunos = alunoDaoRepositorio.findByNomeContainingIgnoreCase(nome);
		}
		modelAndView.addObject("alunosList", listAlunos);
		modelAndView.setViewName("aluno/alunos");
		return modelAndView;
	}	
}
