package com.perpus.go.repository.library;

import com.perpus.go.model.library.Library;
import com.perpus.go.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {
    public Optional<Library> findByOwner(User owner);
}
