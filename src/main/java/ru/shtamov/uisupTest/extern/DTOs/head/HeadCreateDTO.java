package ru.shtamov.uisupTest.extern.DTOs.head;

import jakarta.validation.constraints.NotNull;

public record HeadCreateDTO(@NotNull String fullName) {
}
