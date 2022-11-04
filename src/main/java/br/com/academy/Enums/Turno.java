package br.com.academy.Enums;

public enum Turno {
	MANHA("Manhã"),
	TARDE("Tarde"),
	NOITE("Noite"),
	EAD("EAD");
	
	private final String turno;
	
	private Turno(String turno) {
		this.turno = turno;
	}
	
	public String getTurno() {
		return turno;
	}
}
