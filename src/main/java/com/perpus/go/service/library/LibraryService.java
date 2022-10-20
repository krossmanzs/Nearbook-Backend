package com.perpus.go.service.library;

import com.perpus.go.dto.library.AddBookRequest;
import com.perpus.go.dto.library.AddLibraryRequest;
import com.perpus.go.model.library.Library;
import com.perpus.go.model.user.User;

import java.util.Optional;

public interface LibraryService {
    public void saveBook(AddBookRequest bookRequest);
    public void saveLibrary(AddLibraryRequest libraryRequest);
    public Optional<Library> getLibrary(User owner);
}
