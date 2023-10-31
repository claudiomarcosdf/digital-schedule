package br.com.claudio;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DigitalScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalScheduleApplication.class, args);
		System.setProperty("java.awt.headless", "false"); //Para abrir o browser
	}
	
    @Bean
    WebDriver webDriver() {
//        ChromeOptions options = new ChromeOptions(); 
//        options.addArguments("--headless=new");
    	System.setProperty("webdriver.chrome.driver", "C:\\Users\\claud\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        var webDriver = new ChromeDriver();
        webDriver.get("https://web.whatsapp.com/");
        return webDriver;
    }	
	
}
