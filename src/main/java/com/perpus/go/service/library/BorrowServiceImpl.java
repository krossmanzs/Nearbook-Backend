package com.perpus.go.service.library;

import com.perpus.go.exception.AlreadyBorrowedException;
import com.perpus.go.exception.NotFoundException;
import com.perpus.go.model.book.Book;
import com.perpus.go.model.library.Borrower;
import com.perpus.go.model.user.User;
import com.perpus.go.repository.library.BorrowerRepository;
import com.perpus.go.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class BorrowServiceImpl implements BorrowService{
    private final BorrowerRepository borrowerRepository;
    private final UserService userService;
    private final LibraryService libraryService;

    @Override
    public Borrower borrowBook(String borrowerEmail, Long bookId) {
        User user = userService.findUserByEmail(borrowerEmail)
                .orElseThrow(() -> new NotFoundException(borrowerEmail + " not found"));
        Book book = libraryService.getBook(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " not found"));
        if (book.isBorrowed()) {
            throw new AlreadyBorrowedException(book.getTitle() + " already borrowed");
        }
        book.setBorrowed(true);
        Borrower borrower = new Borrower(book, user);
        borrowerRepository.save(borrower);
        return borrower;
    }
}
