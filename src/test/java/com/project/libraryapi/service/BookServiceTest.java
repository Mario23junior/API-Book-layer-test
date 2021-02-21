package com.project.libraryapi.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.project.libraryapi.Model.Book;
import com.project.libraryapi.Service.BookService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {
    
	@Autowired
	BookService service;
	
	@Test
	@DisplayName("Deve Salvar um livro na base de dados")
	public void saveBook() {
		
		//cenario
		Book book = Book.builder().isbn("123").author("marta code").title("a procura de vida microbiana").build();
		
		//Execução
		Book saveBook = service.save(book);
		
		//venificação
		assertThat(saveBook.getId()).isNotNull();
		assertThat(saveBook.getIsbn()).isEqualTo("123");
		assertThat(saveBook.getTitle()).isEqualTo("a procura de vida microbiana");
		assertThat(saveBook.getAuthor()).isEqualTo("fulano");
		
	}
}
