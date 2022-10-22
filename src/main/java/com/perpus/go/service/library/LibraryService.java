package com.perpus.go.service.library;

import com.perpus.go.dto.library.AddBookRequest;
import com.perpus.go.dto.library.RegisterLibraryRequest;
import com.perpus.go.model.book.Book;
import com.perpus.go.model.library.Library;
import com.perpus.go.model.user.User;

import java.util.List;
import java.util.Optional;

public interface LibraryService {
    public void saveBook(String email, AddBookRequest bookRequest);
    public void saveLibrary(String email, RegisterLibraryRequest libraryRequest);
    public Library getLibrary(Long ownerEmail);
    public Optional<Library> getLibrary(User owner);
    public List<Library> getAllLibraries();
    public List<Book> getAllBooks();

    public Optional<Book> getBook(Long bookId);
}
