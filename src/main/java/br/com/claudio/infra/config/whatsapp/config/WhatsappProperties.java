package br.com.claudio.infra.config.whatsapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.whatsapp")
public class WhatsappProperties {
	
	//local or server
	private Boolean useWhatsapp = true;
	private String appEnv = "local";
	private String localUrlWhatsappApi = "http://localhost:3333";
	private String serverUrlWhatsappApi = "https://...";
	private int key = 190;
	private String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
	
	
	public Boolean useWhatsapp() {
		return this.useWhatsapp;
	}
	
	public String getPathMessages() {
		if (this.appEnv == "local") return "c:\\agendadigital\\";
		else return System.getProperty("user.home")+"\\";
	}	
	
	public String getUrlWhatsappApi() {
		if (this.appEnv == "local") return this.localUrlWhatsappApi;
		else return this.serverUrlWhatsappApi;
	}		
	
	public String getAppEnv() {
		return appEnv;
	}

	public void setAppEnv(String appEnv) {
		this.appEnv = appEnv;
	}
	

	public String getLocalUrlWhatsappApi() {
		return localUrlWhatsappApi;
	}

	public void setLocalUrlWhatsappApi(String localUrlWhatsappApi) {
		this.localUrlWhatsappApi = localUrlWhatsappApi;
	}

	public String getServerUrlWhatsappApi() {
		return serverUrlWhatsappApi;
	}

	public void setServerUrlWhatsappApi(String serverUrlWhatsappApi) {
		this.serverUrlWhatsappApi = serverUrlWhatsappApi;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}	
	
}
