package com.perpus.go.model.library;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.perpus.go.model.book.Book;
import com.perpus.go.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Borrower {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = Book.class)
    private Book book;

    @CreatedDate
    private LocalDate borrowedAt;
    private LocalDate returnedAt;
}
