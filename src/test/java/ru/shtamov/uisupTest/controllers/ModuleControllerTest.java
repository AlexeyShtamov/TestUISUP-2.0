package ru.shtamov.uisupTest.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.shtamov.uisupTest.extern.DTOs.module.ModuleCreateDTO;
import ru.shtamov.uisupTest.extern.DTOs.module.ModuleGetDTO;
import ru.shtamov.uisupTest.extern.assemblers.ModuleAssembler;
import ru.shtamov.uisupTest.extern.controllers.ModuleController;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;
import ru.shtamov.uisupTest.service.ModuleService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModuleControllerTest {

    @Mock
    private ModuleService moduleService;

    @Mock
    private ModuleAssembler moduleAssembler;

    @InjectMocks
    private ModuleController moduleController;

    private ModuleCreateDTO moduleCreateDTO;
    private ModuleGetDTO moduleGetDTO;
    private final String uuid = "test-uuid";

    @BeforeEach
    void setUp() {
        moduleCreateDTO = new ModuleCreateDTO("title", "moduleType");
        moduleGetDTO = new ModuleGetDTO("test-uuid", "title", "moduleType");
    }

    @Test
    void testCreate() throws IllegalAccessException, IsAlreadyExistException {
        when(moduleService.createModule(any())).thenReturn(null);
        when(moduleAssembler.fromDTOToModule(moduleCreateDTO)).thenReturn(null);
        when(moduleAssembler.fromModuleToDTO(null)).thenReturn(moduleGetDTO);

        ResponseEntity<ModuleGetDTO> response = moduleController.create(moduleCreateDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(moduleGetDTO, response.getBody());
    }

    @Test
    void testGet() {
        when(moduleService.getModule(uuid)).thenReturn(null);
        when(moduleAssembler.fromModuleToDTO(null)).thenReturn(moduleGetDTO);

        ResponseEntity<ModuleGetDTO> response = moduleController.get(uuid);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(moduleGetDTO, response.getBody());
    }

    @Test
    void testUpdate() throws IllegalAccessException {
        when(moduleService.updateModule(any(), eq(uuid))).thenReturn(null);
        when(moduleAssembler.fromDTOToModule(moduleCreateDTO)).thenReturn(null);
        when(moduleAssembler.fromModuleToDTO(null)).thenReturn(moduleGetDTO);

        ResponseEntity<ModuleGetDTO> response = moduleController.update(moduleCreateDTO, uuid);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(moduleGetDTO, response.getBody());
    }

    @Test
    void testDelete() {
        doNothing().when(moduleService).deleteModule(uuid);

        ResponseEntity<?> response = moduleController.delete(uuid);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(moduleService, times(1)).deleteModule(uuid);
    }
}
