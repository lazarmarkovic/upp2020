package ftn.uns.ac.rs.upp2020.service;

import ftn.uns.ac.rs.upp2020.domain.Book;

public interface BookService {

    Book findBookByTitle(String title) throws Exception;
    void markPlagiarism(Book book);
}
