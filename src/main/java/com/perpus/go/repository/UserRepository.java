package com.perpus.go.repository;

import com.perpus.go.model.User;
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

    @Query(value = "UPDATE user SET enabled = true WHERE id = :id", nativeQuery = true)
    @Modifying
    public void enable(int id);

    @Query(value = "SELECT u FROM User u WHERE u.email = ?1 AND u.verificationCode = ?2")
    public User findByVerficationcode(String email, String code);
}
