package com.perpus.go.controller.library;

import com.perpus.go.dto.library.AddBookRequest;
import com.perpus.go.dto.library.RegisterLibraryRequest;
import com.perpus.go.service.library.LibraryService;
import com.perpus.go.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class BookController {

    private final LibraryService libraryService;

    @PostMapping("/library/book/add")
    public ResponseEntity<?> registerLibrary(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @RequestBody AddBookRequest addBookRequest) {
        String email = Util.getEmailFromAccessToken(accessToken);
        libraryService.saveBook(email, addBookRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
