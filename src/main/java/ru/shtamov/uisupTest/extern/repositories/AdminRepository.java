package ru.shtamov.uisupTest.extern.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shtamov.uisupTest.domain.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsername(String username);
}
