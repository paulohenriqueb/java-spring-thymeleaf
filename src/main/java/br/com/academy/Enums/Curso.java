package br.com.academy.Enums;

public enum Curso {
	ADMINISTRACAO("Administração"),
	INFORMATICA("Informática"),
	CIENCIAS("Ciências"),
	PROGRAMACAO("Programação"),
	CONTABILIDADE("Contabilidade"),
	ENFERMAGEM("Enfermagem");
	
	private final String curso;
	
	private Curso(String curso) {
		this.curso = curso;
	}
	
	public String getCurso() {
        return curso;
    }
	
}
