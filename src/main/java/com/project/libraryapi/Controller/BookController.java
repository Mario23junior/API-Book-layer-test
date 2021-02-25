package com.project.libraryapi.Controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.libraryapi.Controller.Exception.ApiErros;
import com.project.libraryapi.Exception.BusinessException;
import com.project.libraryapi.Model.Book;
import com.project.libraryapi.Service.BookService;
import com.project.libraryapi.dtoModel.BookDTO;

@RestController
@RequestMapping("/api/books")
public class BookController {
    
	private BookService service;
	private ModelMapper modelMapper;
	
	public BookController(BookService service, ModelMapper modelMapper) {
	    this.service = service;
	    this.modelMapper = modelMapper;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookDTO create(@RequestBody @Valid BookDTO dto) {
		Book basePrenche = modelMapper.map(dto, Book.class);
		basePrenche = service.save(basePrenche);
		return modelMapper.map(basePrenche, BookDTO.class);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiErros handleValidationException(MethodArgumentNotValidException ex) {
		 BindingResult bindingResult = ex.getBindingResult();		 
		 return new ApiErros(bindingResult);
	}
	
	@ExceptionHandler(BusinessException .class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErros handleBusinessException(BusinessException ex) {
	    return new ApiErros(ex);
    }
	
	@GetMapping("/{id}")
	public BookDTO get(@PathVariable Long id) {
		Book book = service.getById(id).get();
		 return modelMapper.map(book, BookDTO.class);
	}

}














