package br.com.claudio.infra.config.db.schemas.enums;

public enum Gender {
	
	FEMININO("Feminino"),
	MASCULINO("Masculino");
	
	private String description;
	
	Gender(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

}
