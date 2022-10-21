package com.perpus.go.controller.library;

import com.perpus.go.dto.library.AddBookRequest;
import com.perpus.go.model.library.Borrower;
import com.perpus.go.service.library.BorrowService;
import com.perpus.go.service.library.LibraryService;
import com.perpus.go.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class BookController {

    private final LibraryService libraryService;
    private final BorrowService borrowService;

    @PostMapping("/library/book/add")
    public ResponseEntity<?> registerLibrary(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @RequestBody AddBookRequest addBookRequest) {
        String email = Util.getEmailFromAccessToken(accessToken);
        libraryService.saveBook(email, addBookRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/library/borrow/book/{book_id}")
    public ResponseEntity<?> borrowBook(
            @PathVariable("book_id") Long bookId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
        String email = Util.getEmailFromAccessToken(accessToken);
        Borrower borrower = borrowService.borrowBook(email, bookId);
        Map<String, String> response = new HashMap<>();
        response.put("borrower", borrower.getUser().getName());
        response.put("bookTitle", borrower.getBook().getTitle());
        response.put("qrCode", borrower.getQrCode());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/library/borrow/scan/{scan_id}")
    public ResponseEntity<?> scanBorrowBook(
            @PathVariable("scan_id") String scanId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
        String email = Util.getEmailFromAccessToken(accessToken);
        borrowService.acceptBorrow(email, scanId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/library/return/book/{book_id}")
    public ResponseEntity<?> returnBook(
            @PathVariable("book_id") Long bookId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
        String email = Util.getEmailFromAccessToken(accessToken);
        borrowService.returnBook(email, bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
