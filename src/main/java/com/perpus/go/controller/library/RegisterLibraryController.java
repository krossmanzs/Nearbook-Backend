package com.perpus.go.controller.library;

import com.perpus.go.dto.library.RegisterLibraryRequest;
import com.perpus.go.service.library.LibraryService;
import com.perpus.go.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class RegisterLibraryController {
    private final LibraryService libraryService;

    @PostMapping("/library/register")
    public ResponseEntity<?> registerLibrary(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @Valid @RequestBody RegisterLibraryRequest registerLibraryRequest) {
        String email = Util.getEmailFromAccessToken(accessToken);
        libraryService.saveLibrary(email, registerLibraryRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
