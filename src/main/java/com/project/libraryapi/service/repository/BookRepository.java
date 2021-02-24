package com.project.libraryapi.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.libraryapi.Model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	boolean existsByIsbn(String isbn);

}
