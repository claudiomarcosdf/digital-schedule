package br.com.claudio.infra.config.whatsapp;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import org.springframework.stereotype.Component;

@Component
public class SimpleMessage {
	
    public void sendMessage(String url) {
   	
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(URI.create(url));
                }
            }
        } catch (IOException | InternalError e) {
            e.printStackTrace();
        }    	

    }


}
