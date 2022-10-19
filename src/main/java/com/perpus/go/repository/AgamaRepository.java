package com.perpus.go.repository;

import com.perpus.go.model.ktp.Agama;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgamaRepository extends JpaRepository<Agama, Integer> {
    public Optional<Agama> findByName(String nameAgama);
}
