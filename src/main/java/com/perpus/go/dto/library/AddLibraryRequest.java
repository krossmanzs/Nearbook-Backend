package com.perpus.go.dto.library;

import com.perpus.go.model.book.Book;
import com.perpus.go.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.Set;

@Data
@AllArgsConstructor
public class AddLibraryRequest {
    private String name;
    private String address;
    private String wallpaperImg;
    private Set<Book> books;
    private User owner;
    private Collection<String> galleries;
    private int monthlyVisitor;
    private boolean isOpen;
}
