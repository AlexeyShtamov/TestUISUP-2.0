package ru.shtamov.uisupTest.extern.DTOs.module;

import jakarta.validation.constraints.NotNull;
import ru.shtamov.uisupTest.extern.DTOs.educProg.EducProgInnerDTO;

import java.util.List;

public record ModuleCreateDTO(@NotNull String title, @NotNull String moduleType) {
}
