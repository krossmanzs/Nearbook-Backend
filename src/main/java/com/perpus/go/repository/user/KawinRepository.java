package com.perpus.go.repository.user;

import com.perpus.go.model.user.ktp.Kawin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KawinRepository extends JpaRepository<Kawin, Integer> {
    public Optional<Kawin> findByKode(int kodeKawin);
}
