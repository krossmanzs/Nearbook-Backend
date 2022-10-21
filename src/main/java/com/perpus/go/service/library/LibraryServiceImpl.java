package com.perpus.go.service.library;

import com.perpus.go.dto.library.AddBookRequest;
import com.perpus.go.dto.library.RegisterLibraryRequest;
import com.perpus.go.exception.AlreadyExistException;
import com.perpus.go.exception.NotFoundException;
import com.perpus.go.model.book.Book;
import com.perpus.go.model.library.Library;
import com.perpus.go.model.user.User;
import com.perpus.go.repository.book.BookRepository;
import com.perpus.go.repository.library.LibraryRepository;
import com.perpus.go.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService{
    private final UserService userService;
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;

    @Override
    public void saveBook(String email, AddBookRequest bookRequest) {
        User owner = userService.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException(email + " user not found"));

        Library library = libraryRepository.findByOwner(owner)
                .orElseThrow(() -> new NotFoundException(owner.getEmail() + "'s library not found, please register library first"));

        Book book = new Book(bookRequest);
        book.setLibrary(library);
        bookRepository.save(book);
    }

    @Override
    public void saveLibrary(String email, RegisterLibraryRequest libraryRequest) {
        User owner = userService.findUserByEmail(email)
                        .orElseThrow(() -> new NotFoundException(email + " user not found"));
        Optional<Library> library = libraryRepository.findByOwner(owner);
        if (library.isPresent()) {
            throw new AlreadyExistException(owner.getEmail() + " Already have library");
        }
        libraryRepository.save(new Library(libraryRequest, owner));
    }

    @Override
    public Optional<Library> getLibrary(User owner) {
        return libraryRepository.findByOwner(owner);
    }

    @Override
    public List<Library> getAllLibraries() {
        return libraryRepository.findAll();
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBook(Long bookId) {
        return bookRepository.findById(bookId);
    }
}
