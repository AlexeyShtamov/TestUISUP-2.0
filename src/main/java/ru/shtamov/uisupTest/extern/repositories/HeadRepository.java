package ru.shtamov.uisupTest.extern.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shtamov.uisupTest.domain.Head;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HeadRepository extends JpaRepository<Head, UUID> {
    Optional<Head> findByFullName(String fullName);
}
