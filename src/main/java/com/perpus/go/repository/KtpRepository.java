package com.perpus.go.repository;

import com.perpus.go.model.ktp.Ktp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KtpRepository extends JpaRepository<Ktp, Integer> {
    public void findByNik(Long nik);
}
