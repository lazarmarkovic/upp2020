package ftn.uns.ac.rs.upp2020.camunda_delegate.book_publish.working;


import ftn.uns.ac.rs.upp2020.domain.Book;
import ftn.uns.ac.rs.upp2020.dto.FileHolderDTO;
import ftn.uns.ac.rs.upp2020.repository.BookRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProcessTranscriptDelegate implements JavaDelegate {

    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("--- Task: process uploaded transcript");

        List<FileHolderDTO> pdfFiles = (List<FileHolderDTO>) delegateExecution.getVariable("files");

        Long bookId = (Long) delegateExecution.getVariable("bookId");
        Optional<Book> book = bookRepository.findById(bookId);

        book.get().setTranscript(pdfFiles.get(0).getFile());
    }
}
