package com.perpus.go.controller.library;

import com.perpus.go.dto.library.BookDetailResponse;
import com.perpus.go.dto.library.LibraryDetailResponse;
import com.perpus.go.exception.NotFoundException;
import com.perpus.go.model.library.Library;
import com.perpus.go.model.user.User;
import com.perpus.go.service.library.LibraryService;
import com.perpus.go.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class LibraryController {
    private final LibraryService libraryService;
    private final UserService userService;

    @GetMapping("/library")
    public ResponseEntity<Library> getLibraryDetail(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
        User user = userService.getUserByAccToken(accessToken);
        Library library = libraryService.getLibrary(user)
                .orElseThrow(() -> new NotFoundException(user.getEmail() + " does not have library"));

        return new ResponseEntity<>(library, HttpStatus.OK);
    }

    @GetMapping("/library/book/all")
    public ResponseEntity<List<BookDetailResponse>> getBookList() {
        List<BookDetailResponse> bookDetailList = new ArrayList<>();
        libraryService.getAllBooks()
                .forEach(book -> bookDetailList.add(new BookDetailResponse(book)));
        return new ResponseEntity<>(bookDetailList, HttpStatus.OK);
    }

    @GetMapping("/library/all")
    public ResponseEntity<List<LibraryDetailResponse>> getLibrarylist() {
        List<LibraryDetailResponse> libraryList = new ArrayList<>();
        libraryService.getAllLibraries()
                .forEach(library -> libraryList.add(new LibraryDetailResponse(library)));
        return new ResponseEntity<>(libraryList, HttpStatus.OK);
    }
}
