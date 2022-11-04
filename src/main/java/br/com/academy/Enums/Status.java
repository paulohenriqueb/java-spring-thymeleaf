package br.com.academy.Enums;

public enum Status {
	ATIVO("Ativo"),
	INATIVO("Inativo"),
	TRANCADO("Trancado"),
	CANCELADO("Cancelado");
	
	private final String status;
	
	private Status(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
}
