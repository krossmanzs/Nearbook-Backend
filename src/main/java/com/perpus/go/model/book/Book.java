package com.perpus.go.model.book;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.perpus.go.dto.library.AddBookRequest;
import com.perpus.go.model.library.Library;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(targetEntity = Library.class)
    @JoinColumn(name = "library_id", nullable = false)
    @JsonBackReference
    private Library library;
    private String coverImg;
    private String title;

    @ElementCollection
    private Collection<String> authors = new ArrayList<>();

    @ElementCollection
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

    public Book(AddBookRequest bookRequest) {
        this.coverImg = bookRequest.getCoverImg();
        this.title = bookRequest.getTitle();
        this.authors = bookRequest.getAuthors();
        this.genre = bookRequest.getGenre();
        this.totalPages = bookRequest.getTotalPages();
        this.rating = bookRequest.getRating();
        this.popularity = bookRequest.getPopularity();
        this.language = bookRequest.getLanguage();
        this.description = bookRequest.getDescription();
        this.ebook = bookRequest.isEbook();
        this.physicBook = bookRequest.isPhysicBook();
        this.borrowed = false;
        this.loanDeadLine = bookRequest.getLoanDeadLine();
        this.publishedDate = bookRequest.getPublishedDate();
        this.publisher = bookRequest.getPublisher();
    }
}
