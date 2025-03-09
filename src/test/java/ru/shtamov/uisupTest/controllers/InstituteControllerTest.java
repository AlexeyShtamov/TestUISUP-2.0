package ru.shtamov.uisupTest.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.shtamov.uisupTest.extern.DTOs.institute.InstituteCreateDTO;
import ru.shtamov.uisupTest.extern.DTOs.institute.InstituteGetDTO;
import ru.shtamov.uisupTest.extern.assemblers.InstituteAssembler;
import ru.shtamov.uisupTest.extern.controllers.InstituteController;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;
import ru.shtamov.uisupTest.service.InstituteService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstituteControllerTest {

    @Mock
    private InstituteService instituteService;

    @Mock
    private InstituteAssembler instituteAssembler;

    @InjectMocks
    private InstituteController instituteController;

    private InstituteCreateDTO instituteCreateDTO;
    private InstituteGetDTO instituteGetDTO;
    private final String uuid = "test-uuid";

    @BeforeEach
    void setUp() {
        instituteCreateDTO = new InstituteCreateDTO("title");
        instituteGetDTO = new InstituteGetDTO("test-uuid", "title", List.of());
    }

    @Test
    void testCreate() throws IsAlreadyExistException {
        when(instituteService.createInstitute(any())).thenReturn(null);
        when(instituteAssembler.fromDTOToInstitute(instituteCreateDTO)).thenReturn(null);
        when(instituteAssembler.fromInstituteToDTO(null)).thenReturn(instituteGetDTO);

        ResponseEntity<InstituteGetDTO> response = instituteController.create(instituteCreateDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(instituteGetDTO, response.getBody());
    }

    @Test
    void testGet() {
        when(instituteService.getInstitute(uuid)).thenReturn(null);
        when(instituteAssembler.fromInstituteToDTO(null)).thenReturn(instituteGetDTO);

        ResponseEntity<InstituteGetDTO> response = instituteController.get(uuid);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(instituteGetDTO, response.getBody());
    }

    @Test
    void testUpdate() {
        when(instituteService.updateInstitute(any(), eq(uuid))).thenReturn(null);
        when(instituteAssembler.fromDTOToInstitute(instituteCreateDTO)).thenReturn(null);
        when(instituteAssembler.fromInstituteToDTO(null)).thenReturn(instituteGetDTO);

        ResponseEntity<InstituteGetDTO> response = instituteController.update(instituteCreateDTO, uuid);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(instituteGetDTO, response.getBody());
    }

    @Test
    void testDelete() {
        doNothing().when(instituteService).deleteInstitute(uuid);

        ResponseEntity<?> response = instituteController.delete(uuid);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(instituteService, times(1)).deleteInstitute(uuid);
    }
}
