package ru.shtamov.uisupTest.extern.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shtamov.uisupTest.domain.EducationProgram;

import java.util.Optional;
import java.util.UUID;

public interface EducationProgramRepository extends JpaRepository<EducationProgram, UUID> {

    Optional<EducationProgram> findByTitle(String title);
}
