package com.project.libraryapi.api.resource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class BookControllerTest {
     
	 @Autowired
	 MockMvc mvc;
	 
	@Test
	@DisplayName("Metodo para salvar livros no banco")
	public void createBookTest() {
		
	}
	
	@Test
	@DisplayName("Lançar erro quando não tentar inserir dados invalidos")
	public void createInvalidBookTest() {
		
	}
}
