package com.perpus.go.service.library;

import com.perpus.go.exception.AlreadyBorrowedException;
import com.perpus.go.exception.BadRequestException;
import com.perpus.go.exception.NotFoundException;
import com.perpus.go.model.book.Book;
import com.perpus.go.model.library.Borrower;
import com.perpus.go.model.user.User;
import com.perpus.go.repository.library.BorrowerRepository;
import com.perpus.go.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

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

    @Override
    public Borrower getBorrowerByQrCodeAndEmail(String email, String scanId) {
        User user = userService.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException(email + " not found"));
        Borrower borrower = borrowerRepository
                .findByUserAndQrCode(user.getId(), scanId)
                .orElseThrow(() -> new NotFoundException(email + " does not have qr code with" + scanId));
        if (borrower.getBorrowedAt() != null) {
            throw new AlreadyBorrowedException(borrower.getBook().getTitle() + " already borrowed successfully");
        }
        return (borrower);
    }

    @Override
    public void acceptBorrow(String email, String scanId) {
        Borrower borrower = getBorrowerByQrCodeAndEmail(email, scanId);
        borrower.setBorrowedAt(LocalDate.now());
    }

    @Override
    public void returnBook(String email, Long bookId) {
        Borrower borrower = borrowerRepository.findByBookId(bookId)
                .orElseThrow(() -> new NotFoundException("No one borrowing book with id " + bookId));
        if (borrower.getBorrowedAt() == null) {
                throw new NotFoundException("No one borrowing book with id " + bookId);
        } else if (!borrower.getBook().getLibrary().getOwner().getEmail().equals(email)) {
            throw new BadRequestException("Only the owner can post if the book is returned");
        }
        borrower.setReturnedAt(LocalDate.now());
        borrower.getBook().setBorrowed(false);
    }
}
