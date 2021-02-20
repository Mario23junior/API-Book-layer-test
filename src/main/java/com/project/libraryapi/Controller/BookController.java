package com.project.libraryapi.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.libraryapi.Model.Book;
import com.project.libraryapi.Service.BookService;
import com.project.libraryapi.dtoModel.BookDTO;

@RestController
@RequestMapping("/api/books/")
public class BookController {
    
	private BookService service;
	
	public BookController(BookService service) {
	    this.service = service;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookDTO create(@RequestBody BookDTO dto) {
		Book basePrenche = Book.builder()
				              .author(dto.getAuthor())
				              .title(dto.getTitle())
				              .isbn(dto.getIsbn())
				              .build();
		
		basePrenche = service.save(basePrenche);
		
		return BookDTO.builder()
				          .id(basePrenche.getId())   
				          .author(basePrenche.getAuthor())
				          .title(basePrenche.getTitle())
				          .isbn(basePrenche.getIsbn())
				          .build();
				          
				          
	}
}
