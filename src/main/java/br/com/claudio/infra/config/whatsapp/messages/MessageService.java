package br.com.claudio.infra.config.whatsapp.messages;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import br.com.claudio.infra.config.whatsapp.config.WhatsappProperties;

@Service
public class MessageService {
	
	private final WhatsappProperties whatsappProperties;
	
	public MessageService(WhatsappProperties whatsappProperties) {
		this.whatsappProperties = whatsappProperties;
	}

	private Path getFilePath(String fileName) throws IOException {
		String pathDefault = whatsappProperties.getPathMessages();
		
		Path path = Path.of(pathDefault+fileName);
		
		if (!Files.exists(path)) {
			Path newDirectory = Files.createDirectories(path.getParent().resolve(""));
			Path newFile = Files.createFile(newDirectory.resolve(fileName));
			
			return newFile;
		}
		
		return path;
	}
	
	public String getFileContent(String fileName) throws IOException {
		Path path = getFilePath(fileName+".txt");
		
		return Files.readString(path, UTF_8);
	}
	
	public void saveFileContent(String fileName, String content) throws IOException {
		Path path = getFilePath(fileName+".txt");
		
		Files.writeString(path, content, UTF_8);
	}
	

}
