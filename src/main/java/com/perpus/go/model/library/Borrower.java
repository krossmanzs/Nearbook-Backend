package com.perpus.go.model.library;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.perpus.go.model.book.Book;
import com.perpus.go.model.user.User;
import com.perpus.go.util.Util;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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

    private String qrCode;

    @CreationTimestamp
    private LocalDate createdAt;
    private LocalDate borrowedAt;
    private LocalDate returnedAt;

    public Borrower(Book book, User user) {
        this.user = user;
        this.book = book;
        this.qrCode = Util.getRandomUUID();
        this.borrowedAt = null;
        this.returnedAt = null;
    }
}
