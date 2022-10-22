package com.perpus.go.service.library;

import com.perpus.go.model.library.Borrower;

import java.util.List;


public interface BorrowService {
    public Borrower borrowBook(String borrowerEmail, Long bookId);
    public Borrower getBorrowerByQrCodeAndEmail(String email,String scanId);
    public void acceptBorrow(String email, String scanId);
    public void returnBook(String email, Long bookId);

    Borrower getBorrowerById(Long borrowId);
    List<Borrower> getAllBorrower();
}
