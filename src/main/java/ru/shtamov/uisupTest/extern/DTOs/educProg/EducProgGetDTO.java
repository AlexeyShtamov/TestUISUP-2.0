package ru.shtamov.uisupTest.extern.DTOs.educProg;

import ru.shtamov.uisupTest.extern.DTOs.head.HeadGetDTO;
import ru.shtamov.uisupTest.extern.DTOs.institute.InstituteInnerDTO;

import java.time.LocalDate;

public record EducProgGetDTO(String uuid, String title, String cypher, String level, String standard, InstituteInnerDTO institute, HeadGetDTO head, LocalDate accreditationDate) {
}
