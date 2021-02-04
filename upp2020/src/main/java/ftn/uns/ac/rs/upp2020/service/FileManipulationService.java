package ftn.uns.ac.rs.upp2020.service;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface FileManipulationService {
    Resource downloadTranscript();
    void saveTranscript(MultipartFile file) throws IOException;
}
