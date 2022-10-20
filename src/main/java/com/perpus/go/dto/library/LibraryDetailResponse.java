package com.perpus.go.dto.library;

import com.perpus.go.model.book.Book;
import com.perpus.go.model.library.Library;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
public class LibraryDetailResponse {
    private Long id;
    private String name;
    private String address;
    Set<Book> books;
    private OwnerMeta owner;
    private boolean isOpen;

    public LibraryDetailResponse(Library library) {
        this.id = library.getId();
        this.name = library.getName();
        this.address = library.getAddress();
        this.books = library.getBooks();
        this.owner = new OwnerMeta(
                library.getOwner().getName(),
                library.getOwner().getEmail(),
                library.getOwner().getGender()
        );
        this.isOpen = library.isOpen();
    }
}

@Data
@AllArgsConstructor
class OwnerMeta {
    private String name;
    private String email;
    private String gender;
}
