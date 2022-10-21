package com.perpus.go.repository.library;

import com.perpus.go.model.library.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    @Query(value = "SELECT * FROM Borrower WHERE user_id = ?1 AND qr_code = ?2", nativeQuery = true)
    Optional<Borrower> findByUserAndQrCode(Integer userId, String scanId);
    Optional<Borrower> findByBookId(Long bookId);
}
