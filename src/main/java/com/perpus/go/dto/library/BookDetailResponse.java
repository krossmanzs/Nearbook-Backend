package com.perpus.go.dto.library;

import com.perpus.go.model.book.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailResponse {
    private Long id;
    private LibraryMeta library;
    private String coverImg;
    private String title;
    private Collection<String> authors = new ArrayList<>();
    private Collection<String> genre = new ArrayList<>();
    private int totalPages;
    private float rating;
    private int popularity;
    private String language;
    private String description;
    private boolean ebook;
    private boolean physicBook;
    private boolean borrowed;
    private int loanDeadLine;
    private LocalDate publishedDate;
    private String publisher;
    @CreationTimestamp
    private LocalDate createdAt;
    @Transient
    @UpdateTimestamp
    private LocalDate updatedAt;

    public BookDetailResponse(Book book) {
        this.id = book.getId();
        this.library = new LibraryMeta(
                book.getLibrary().getId(),
                book.getLibrary().getName(),
                book.getLibrary().getAddress(),
                book.getLibrary().getOwner().getName()
        );
        this.coverImg = book.getCoverImg();
        this.title = book.getTitle();
        this.authors = book.getAuthors();
        this.genre = book.getGenre();
        this.totalPages = book.getTotalPages();
        this.rating = book.getRating();
        this.popularity = book.getPopularity();
        this.language = book.getLanguage();
        this.description = book.getDescription();
        this.ebook = book.isEbook();
        this.physicBook = book.isPhysicBook();
        this.borrowed = book.isBorrowed();
        this.loanDeadLine = book.getLoanDeadLine();
        this.publishedDate = book.getPublishedDate();
        this.publisher = book.getPublisher();
        this.createdAt = book.getCreatedAt();
        this.updatedAt = book.getUpdatedAt();
    }
}

@Data
@AllArgsConstructor
class LibraryMeta {
    private Long id;
    private String name;
    private String address;
    private String owner;
}
