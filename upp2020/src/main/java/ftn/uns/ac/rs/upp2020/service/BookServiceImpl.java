package ftn.uns.ac.rs.upp2020.service;

import ftn.uns.ac.rs.upp2020.domain.Book;
import ftn.uns.ac.rs.upp2020.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book findBookByTitle(String title) throws Exception{
        Optional<Book> book = bookRepository.findByTitle(title);
        if (book.isEmpty()){
            throw new Exception(String.format("No book with %s title", title));
        }
        return book.get();
    }
}
