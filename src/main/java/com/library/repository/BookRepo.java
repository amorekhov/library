package com.library.repository;

import com.library.domain.Book;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo extends JpaRepository <Book, Long>  {

   // Book findById(String bookId);

}
