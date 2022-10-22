package com.perpus.go.controller.library;

import com.perpus.go.dto.library.AddBookRequest;
import com.perpus.go.dto.library.borrower.BorrowerDetailResponse;
import com.perpus.go.exception.BadRequestException;
import com.perpus.go.exception.NotFoundException;
import com.perpus.go.model.book.Book;
import com.perpus.go.model.library.Borrower;
import com.perpus.go.model.library.Library;
import com.perpus.go.service.library.BorrowService;
import com.perpus.go.service.library.LibraryService;
import com.perpus.go.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @GetMapping("/library/book/{book_id}/detail")
    public ResponseEntity<?> getBookDetail(
            @PathVariable("book_id") Long bookId
    ) {
        Book book = libraryService.getBook(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " not found"));
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/library/{lib_id}/detail")
    public ResponseEntity<?> getLibraryDetail(
            @PathVariable("lib_id") Long libraryId
    ) {
        Library library = libraryService.getLibrary(libraryId);
        return new ResponseEntity<>(library, HttpStatus.OK);
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

    @GetMapping("/library/borrow/scan/get-qr/{borrow_id}")
    public ResponseEntity<?> getQrScanCode(
            @PathVariable("borrow_id") Long borrowId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
        String email = Util.getEmailFromAccessToken(accessToken);
        Borrower borrower = borrowService.getBorrowerById(borrowId);
        if (!borrower.getUser().getEmail().equals(email)) {
            throw new BadRequestException("You are not the borrower of id " + borrowId);
        }
        HashMap<String, String> response = new HashMap<>();
        response.put("qrCode", borrower.getQrCode());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/library/borrow/get-detail/{borrow_id}")
    public ResponseEntity<?> getBorrowerDetail(
            @PathVariable("borrow_id") Long borrowId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
        String email = Util.getEmailFromAccessToken(accessToken);
        Borrower borrower = borrowService.getBorrowerById(borrowId);
        if (!borrower.getUser().getEmail().equals(email)) {
            throw new BadRequestException("You are not the borrower of id " + borrowId);
        }
        return new ResponseEntity<>(borrower, HttpStatus.OK);
    }

    @GetMapping("/library/borrower/get-all")
    public ResponseEntity<?> getListBorrower() {
        List<BorrowerDetailResponse> response = new ArrayList<>();
        borrowService.getAllBorrower()
                .forEach(borrower -> response.add(new BorrowerDetailResponse(
                    borrower.getId(),
                    borrower.getUser().getName(),
                    borrower.getBook().getTitle(),
                    borrower.getQrCode(),
                    borrower.getCreatedAt(),
                    borrower.getBorrowedAt(),
                    borrower.getReturnedAt()
                )));
        return new ResponseEntity<>(response, HttpStatus.OK);
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
