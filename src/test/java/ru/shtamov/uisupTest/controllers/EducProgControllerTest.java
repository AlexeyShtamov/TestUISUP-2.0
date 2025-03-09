package ru.shtamov.uisupTest.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.shtamov.uisupTest.domain.EducationProgram;
import ru.shtamov.uisupTest.domain.Module;
import ru.shtamov.uisupTest.domain.enums.SortedType;
import ru.shtamov.uisupTest.extern.DTOs.educProg.EducProgCreateDTO;
import ru.shtamov.uisupTest.extern.DTOs.educProg.EducProgGetDTO;
import ru.shtamov.uisupTest.extern.DTOs.educProg.EducProgUpdateDTO;
import ru.shtamov.uisupTest.extern.DTOs.head.HeadGetDTO;
import ru.shtamov.uisupTest.extern.DTOs.institute.InstituteInnerDTO;
import ru.shtamov.uisupTest.extern.assemblers.EducationProgramAssembler;
import ru.shtamov.uisupTest.extern.controllers.EducationProgramController;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;
import ru.shtamov.uisupTest.service.EducationProgramService;
import ru.shtamov.uisupTest.service.ModuleService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EducProgControllerTest {

    @Mock
    private EducationProgramService educProgService;

    @Mock
    private EducationProgramAssembler educProgAssembler;

    @Mock
    private ModuleService moduleService;

    @InjectMocks
    private EducationProgramController controller;

    private EducProgCreateDTO educProgCreateDTO;
    private EducationProgram educationProgram;
    private EducProgGetDTO educProgGetDTO;
    private EducProgUpdateDTO educProgUpdateDTO;

    @BeforeEach
    void setUp() {
        educProgCreateDTO = new EducProgCreateDTO("title", "cypher", "level", "standard", "date", "uuid1", "uuid2");
        educProgUpdateDTO = new EducProgUpdateDTO("title", "cypher", "level", "standard", "date");
        educationProgram = new EducationProgram();
        educProgGetDTO = new EducProgGetDTO("uuid", "title", "cypher", "level", "standard", new InstituteInnerDTO("uuid", "title"), new HeadGetDTO("uuid", "fullName"), LocalDate.now());
    }

    @Test
    void testCreate() throws IsAlreadyExistException {
        when(educProgAssembler.fromDTOToEducProg(educProgCreateDTO)).thenReturn(educationProgram);
        when(educProgService.createEducationProgram(educationProgram)).thenReturn(educationProgram);
        when(educProgAssembler.fromEducProgToDTO(educationProgram)).thenReturn(educProgGetDTO);

        ResponseEntity<EducProgGetDTO> response = controller.create(educProgCreateDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(educProgGetDTO, response.getBody());
    }

    @Test
    void testGet() {
        String uuid = "test-uuid";
        when(educProgService.getEducationProgram(uuid)).thenReturn(educationProgram);
        when(educProgAssembler.fromEducProgToDTO(educationProgram)).thenReturn(educProgGetDTO);

        ResponseEntity<EducProgGetDTO> response = controller.get(uuid);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(educProgGetDTO, response.getBody());
    }

    @Test
    void testUpdate() {
        String uuid = "test-uuid";
        when(educProgAssembler.fromDTOToEducProgUpdate(educProgUpdateDTO)).thenReturn(educationProgram);
        when(educProgService.updateEducationProgram(educationProgram, uuid)).thenReturn(educationProgram);
        when(educProgAssembler.fromEducProgToDTO(educationProgram)).thenReturn(educProgGetDTO);

        ResponseEntity<EducProgGetDTO> response = controller.update(educProgUpdateDTO, uuid);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(educProgGetDTO, response.getBody());
    }

    @Test
    void testDelete() {
        String uuid = "test-uuid";
        doNothing().when(educProgService).deleteEducationProgram(uuid);

        ResponseEntity<?> response = controller.delete(uuid);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetAll() {
        String sortedType = "BY_TITLE";
        List<EducationProgram> programs = List.of(educationProgram);
        List<EducProgGetDTO> dtos = List.of(educProgGetDTO);

        when(educProgService.getAllPrograms(SortedType.valueOf(sortedType))).thenReturn(programs);
        when(educProgAssembler.fromEducProgToDTO(educationProgram)).thenReturn(educProgGetDTO);

        ResponseEntity<List<EducProgGetDTO>> response = controller.getAll(sortedType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtos, response.getBody());
    }

    @Test
    void testAddModule() {
        String uuid = "test-uuid";
        String moduleUuid = "module-uuid";
        Module module = new Module();

        when(moduleService.getModule(moduleUuid)).thenReturn(module);
        when(educProgService.addModule(uuid, module)).thenReturn(educationProgram);
        when(educProgAssembler.fromEducProgToDTO(educationProgram)).thenReturn(educProgGetDTO);

        ResponseEntity<EducProgGetDTO> response = controller.addModule(uuid, moduleUuid);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(educProgGetDTO, response.getBody());
    }
}
