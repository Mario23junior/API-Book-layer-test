package com.project.libraryapi.Service;

import java.util.Optional;

import com.project.libraryapi.Model.Book;

public interface BookService {
     
	Book save(Book any);

   Optional<Book> getById(Long id);
}
