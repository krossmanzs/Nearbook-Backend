package com.perpus.go.repository.user;

import com.perpus.go.model.user.ktp.Ktp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KtpRepository extends JpaRepository<Ktp, Integer> {
    public void findByNik(Long nik);
}
