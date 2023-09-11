package br.com.claudio.infra.config.db.schemas.enums;

public enum StatusSchedule {
	
	AGENDADO("Agendado"),
	CONFIRMADO("Confirmado"),
	PRESENTE("Presente"),
	FINALIZADO("Finalizado"),
	CANCELADO("Cancelado");
	
	private String description;
	
	StatusSchedule(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

}
