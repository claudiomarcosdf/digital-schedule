package br.com.claudio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DigitalScheduleApplication {
	
	private static Logger logger = LoggerFactory.getLogger(DigitalScheduleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DigitalScheduleApplication.class, args);
		//System.setProperty("java.awt.headless", "false"); //Para abrir o browser
	}
	
}
