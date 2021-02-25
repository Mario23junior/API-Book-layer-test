package com.project.libraryapi.api.resource;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.libraryapi.Exception.BusinessException;
import com.project.libraryapi.Model.Book;
import com.project.libraryapi.Service.BookService;
import com.project.libraryapi.dtoModel.BookDTO;


@WebMvcTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class BookControllerTest {
    
	static String BOOK_KEY = "/api/books/";
	
	 @Autowired
	 MockMvc mvc;
	 
	 @MockBean
	 BookService service;
	 
	@Test
	@DisplayName("Metodo para salvar livros no banco")
	public void createBookTest() throws Exception {
		
		BookDTO dto = createNewBook();
		Book saveBook = Book.builder().id(101).author("Arthur").title("harry ventuy").isbn("23112").build();

		BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(saveBook);
		
		String json = new ObjectMapper().writeValueAsString(dto);
		
		MockHttpServletRequestBuilder requestFake = MockMvcRequestBuilders
		           .post(BOOK_KEY)
		           .contentType(MediaType.APPLICATION_JSON)
		           .accept(MediaType.APPLICATION_JSON)
		           .content(json);
		
		mvc
		.perform(requestFake)
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("id").value(101))
		.andExpect(jsonPath("title").value(dto.getTitle()))
	    .andExpect(jsonPath("author").value(dto.getAuthor()))
	    .andExpect(jsonPath("isbn").value(dto.getIsbn()));
	}
	
	@Test
	@DisplayName("Lançar erro quando não tentar inserir dados invalidos")
	public void createInvalidBookTest() throws Exception {
		
		String json = new ObjectMapper().writeValueAsString(new BookDTO());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
		           .post(BOOK_KEY)
		           .contentType(MediaType.APPLICATION_JSON)
		           .accept(MediaType.APPLICATION_JSON)
		           .content(json);
		
		mvc.perform(request)
		        .andExpect(status().isBadRequest())
		        .andExpect(jsonPath("errors",Matchers.hasSize(3)));
	}
	
	@Test
	@DisplayName("Deve lançar erro ao tentar cadastrar um livro com isbn ja cadastrado")
	public void createBookWithDUplicateIsbn() throws Exception {

	 	BookDTO dto = createNewBook();
		String json = new ObjectMapper().writeValueAsString(dto);
		String mensagemErro = "isbn ja cadastrado";
		BDDMockito.given(service.save(Mockito.any(Book.class)))
		                .willThrow(new BusinessException(mensagemErro));
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
		           .post(BOOK_KEY)
		           .contentType(MediaType.APPLICATION_JSON)
		           .accept(MediaType.APPLICATION_JSON)
		           .content(json);
		
		mvc.perform(request)
		           .andExpect(status().isBadRequest())
		           .andExpect(jsonPath("errors",Matchers.hasSize(1) ))
		           .andExpect(jsonPath("errors[0]").value(mensagemErro));
		           
	}
	
	
	@Test
	@DisplayName("Deve onter de um llivros")
	public void getBookDetails() throws Exception {
		
		Long id = 11L;
		
		Book book = Book.builder()
				        .id(id)
				        .title(createNewBook().getTitle())
				        .author(createNewBook().getAuthor())
				        .isbn(createNewBook().getIsbn())
				        .build();
		
		BDDMockito.given(service.getById(id)).willReturn(Optional.of(book));
		
		//execucao
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
		                .get(BOOK_KEY.concat("/" + id))
		                .accept(MediaType.APPLICATION_JSON);
		                
		          mvc
		            .perform(request)
		            .andExpect(status().isOk())
			      	.andExpect(MockMvcResultMatchers.jsonPath("id").value(id))
					.andExpect(jsonPath("title").value(createNewBook().getTitle()))
				    .andExpect(jsonPath("author").value(createNewBook().getAuthor()))
				    .andExpect(jsonPath("isbn").value(createNewBook().getIsbn())); 
		            
		                
		
	}
	
	@Test
	@DisplayName("Deve retornar not found quando o livro não existir  no banco de dados ")
	public void bookNotFoundTest() throws Exception{
		
		// cenario
		BDDMockito.given(service.getById(Mockito.anyLong()) ).willReturn(Optional.empty());

		//execucao
				MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				                .get(BOOK_KEY.concat("/" + 1))
				                .accept(MediaType.APPLICATION_JSON);
				mvc.perform(request)
				       .andExpect(status().isNotFound());
				
	}
	
	@Test
	@DisplayName("Deve deletar um livro")
	public void deleteBookTest() throws Exception {
		BDDMockito.given(service.getById(Mockito.anyLong())).willReturn(Optional.of(Book.builder().id(11).build()));

		//execucao
				MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(BOOK_KEY.concat("/" + 1));
				
				mvc.perform(request)
				   .andExpect(status().isNoContent());
	} 
	
	
	@Test
	@DisplayName("Deve retornar not found quando não encontrar um livro para deletar")
	public void deleteBookTestExeption() throws Exception {
		BDDMockito.given(service.getById(Mockito.anyLong())).willReturn(Optional.empty());

		//execucao
				MockHttpServletRequestBuilder request = MockMvcRequestBuilders
						.delete(BOOK_KEY.concat("/" + 1));
				
				mvc.perform(request)
				   .andExpect(status().isNotFound());
	} 
	
	private BookDTO createNewBook() {
	   return BookDTO.builder().author("Arthur").title("harry ventuy").isbn("23112").build();
	}
	
	
}


















