package com.perpus.go.model.library;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.perpus.go.dto.library.AddLibraryRequest;
import com.perpus.go.model.book.Book;
import com.perpus.go.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Library {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String address;

    private String wallpaperImg;

    // https://stackoverflow.com/questions/3325387/infinite-recursion-with-jackson-json-and-hibernate-jpa-issue/18288939#18288939
    @OneToMany(mappedBy = "library")
    @JsonManagedReference
    private Set<Book> books;

    @OneToOne(targetEntity = User.class)
    private User owner;

    @ElementCollection
    private Collection<String> galleries = new ArrayList<>();

    private int monthlyVisitor;
    private boolean isOpen;

    public Library(AddLibraryRequest libraryRequest) {
        this.name = libraryRequest.getName();
        this.address = libraryRequest.getAddress();
        this.wallpaperImg = libraryRequest.getWallpaperImg();
        this.books = libraryRequest.getBooks();
        this.owner = libraryRequest.getOwner();
        this.galleries = libraryRequest.getGalleries();
        this.monthlyVisitor = libraryRequest.getMonthlyVisitor();
        this.isOpen = libraryRequest.isOpen();
    }
}
