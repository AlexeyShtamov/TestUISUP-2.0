package ru.shtamov.uisupTest.extern.DTOs.educProg;


import jakarta.validation.constraints.NotNull;

public record EducProgCreateDTO(@NotNull String title, @NotNull String cypher, @NotNull String level, @NotNull String standard, @NotNull String accreditationDate, String headUuid, String instituteUuid) {
}
