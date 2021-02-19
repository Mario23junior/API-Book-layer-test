package com.project.libraryapi.api.resource;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;

@WebMvcTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class BookControllerTest {
    
	static String BOOK_KEY = "/api/books/";
	
	 @Autowired
	 MockMvc mvc;
	 
	@Test
	@DisplayName("Metodo para salvar livros no banco")
	public void createBookTest() throws Exception {
		
		String json = new ObjectMapper().writeValueAsString(null);
		
		MockHttpServletRequestBuilder requestFake = MockMvcRequestBuilders
		           .post(BOOK_KEY)
		           .contentType(MediaType.APPLICATION_JSON)
		           .accept(MediaType.APPLICATION_JSON)
		           .content("");
		
		mvc
		.perform(requestFake)
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath(json, "id").isNotEmpty())
		.andExpect(jsonPath("titulo").value("menu livro teste"))
	    .andExpect(jsonPath("author").value("author test"))
	    .andExpect(jsonPath("isbn").value("129949323"));
	
	}
	
	@Test
	@DisplayName("Lançar erro quando não tentar inserir dados invalidos")
	public void createInvalidBookTest() {
		
	}
}
