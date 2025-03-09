package ru.shtamov.uisupTest.extern.DTOs.institute;

import ru.shtamov.uisupTest.extern.DTOs.educProg.EducProgInnerDTO;

import java.util.List;

public record InstituteGetDTO(String uuid, String title, List<EducProgInnerDTO> progs) {
}
