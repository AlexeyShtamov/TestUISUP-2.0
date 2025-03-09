package ru.shtamov.uisupTest.extern.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shtamov.uisupTest.domain.EducationProgram;
import ru.shtamov.uisupTest.domain.Institute;

import java.util.Optional;
import java.util.UUID;

public interface InstituteRepository extends JpaRepository<Institute, UUID> {
    Optional<Institute> findByTitle(String title);
}
