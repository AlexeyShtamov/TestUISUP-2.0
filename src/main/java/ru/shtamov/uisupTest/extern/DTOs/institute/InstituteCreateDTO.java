package ru.shtamov.uisupTest.extern.DTOs.institute;

import jakarta.validation.constraints.NotNull;

public record InstituteCreateDTO(@NotNull String title) {
}
