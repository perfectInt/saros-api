package ru.saros.identityservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saros.identityservice.models.UserCredential;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {
    Optional<UserCredential> findByUsername(String username);
}
