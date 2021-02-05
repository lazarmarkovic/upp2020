package ftn.uns.ac.rs.upp2020.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.UrlResource;

@Service
public class FileManipulationServiceImpl implements FileManipulationService{


    public Resource downloadTranscript() {
        try {
            Path filePath = Paths.get("..\\upp\\upp2020\\upp2020\\src\\main\\resources\\downloads", "download.txt");

            UrlResource urlResource = new UrlResource("file:///" + filePath.toAbsolutePath().toString());
            return urlResource;
        } catch (Exception ex) {
            return null;
        }
    }

	@Override
	public void saveTranscript(MultipartFile payload) {
        Path filepath = Paths.get("..\\upp\\upp2020\\upp2020\\src\\main\\resources\\downloads", payload.getOriginalFilename());

        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(payload.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
