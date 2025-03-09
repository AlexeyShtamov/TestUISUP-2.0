package ru.shtamov.uisupTest.extern.DTOs.module;

import ru.shtamov.uisupTest.extern.DTOs.educProg.EducProgInnerDTO;

import java.util.List;

public record ModuleGetDTO(String uuid, String title, String moduleType, List<EducProgInnerDTO> educProgs) {
}
