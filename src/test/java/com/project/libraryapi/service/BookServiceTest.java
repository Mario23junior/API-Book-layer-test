package com.project.libraryapi.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.project.libraryapi.Exception.BusinessException;
import com.project.libraryapi.Model.Book;
import com.project.libraryapi.Service.BookService;
import com.project.libraryapi.service.impl.BookServiceImpl;
import com.project.libraryapi.service.repository.BookRepository;


@Service
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {
    
	BookService service;
	
	@MockBean
	BookRepository repository;
	
	@BeforeEach
	public void SetUp() {
		 this.service = new BookServiceImpl(repository);
	}
	
	@Test
	@DisplayName("Deve Salvar um livro na base de dados")
	public void saveBookTest() {
		
		//cenario
		Book book = createValidBook();
		Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(false);
		Mockito.when(repository.save(book)).thenReturn
		                                 (Book.builder()
		                                		 .id(11)
		                                		 .isbn("123")
		                                		 .title("a procura de vida microbiana")
		                                		 .author("fulano")
		                                		 .build());
		
		//Execução
		Book saveBook = service.save(book);
		
		//venificação
		assertThat(saveBook.getId()).isNotNull();
		assertThat(saveBook.getIsbn()).isEqualTo("123");
		assertThat(saveBook.getTitle()).isEqualTo("a procura de vida microbiana");
		assertThat(saveBook.getAuthor()).isEqualTo("fulano");
	}
	
	 private Book createValidBook() {
		  return Book.builder().isbn("123").author("marta code").title("a procura de vida microbiana").build();
	   }
        
	   @Test
	   @DisplayName("deve lançar erro de negocio ao tentar salvar um livro com isbn duplicado")
	   public void shuldNotSaveABookWithDuplicateISBN() {
		   //cenario 
		   Book book = createValidBook();
		   
		   Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(true);
		   
		   //execucao
		   Throwable exception = Assertions.catchThrowable(() -> service.save(book));
		   
		   // verificações
		   assertThat(exception)
		                .isInstanceOf(BusinessException.class)
		                .hasMessage("Isbn já cadastrado");
		   
		   Mockito.verify(repository, Mockito.never()).save(book);
		                
		   
		   
		   
	   }
	   
	  
}

















