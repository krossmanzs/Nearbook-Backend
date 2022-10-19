package com.perpus.go.repository.user;

import com.perpus.go.model.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    public Optional<User> findByEmail(String email);

    @Query(value = "SELECT password FROM user WHERE email = :email", nativeQuery = true)
    public String findPasswordByEmail(String email);

    @Query(value = "UPDATE user SET verified_email = true WHERE id = :id", nativeQuery = true)
    @Modifying
    public void verifyEmail(int id);

    @Query(value = "SELECT u FROM User u WHERE u.email = ?1 AND u.verificationCode = ?2")
    public User findByVerficationcode(String email, String code);
}
