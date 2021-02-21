package com.project.libraryapi.service.impl;

import com.project.libraryapi.Model.Book;
import com.project.libraryapi.Service.BookService;
import com.project.libraryapi.service.repository.BookRepository;

public class BookServiceImpl implements BookService {
    
	private BookRepository repository;
	
	public BookServiceImpl(BookRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Book save(Book book) {
		return repository.save(book);
	}

}
