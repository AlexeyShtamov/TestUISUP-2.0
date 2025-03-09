package ru.shtamov.uisupTest.extern.DTOs.educProg;

import jakarta.validation.constraints.NotNull;

public record EducProgUpdateDTO(String title, String cypher, String level, String standard, String accreditationDate) {
}
