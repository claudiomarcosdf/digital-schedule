package br.com.claudio.infra.config.db.schemas.enums;

public enum UserRole {
	
    ADMIN("admin"),
    PROFESSIONAL("professional"),
    USER("user");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }	

}
