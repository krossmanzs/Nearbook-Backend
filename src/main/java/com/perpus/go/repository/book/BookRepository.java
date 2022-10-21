package com.perpus.go.repository.book;

import com.perpus.go.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    public Optional<Book> findById(Long id);
}
