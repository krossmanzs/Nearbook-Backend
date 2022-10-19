package com.perpus.go.repository;

import com.perpus.go.model.ktp.Kawin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KawinRepository extends JpaRepository<Kawin, Integer> {
    public Optional<Kawin> findByKode(int kodeKawin);
}
