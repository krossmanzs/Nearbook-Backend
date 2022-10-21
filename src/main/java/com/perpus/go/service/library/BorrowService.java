package com.perpus.go.service.library;

import com.perpus.go.model.book.Book;
import com.perpus.go.model.library.Borrower;

public interface BorrowService {
    public Borrower borrowBook(String borrowerEmail, Long bookId);
}
