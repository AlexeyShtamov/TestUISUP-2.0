package ru.shtamov.uisupTest.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.shtamov.uisupTest.extern.DTOs.head.HeadCreateDTO;
import ru.shtamov.uisupTest.extern.DTOs.head.HeadGetDTO;
import ru.shtamov.uisupTest.extern.assemblers.HeadAssembler;
import ru.shtamov.uisupTest.extern.controllers.HeadController;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;
import ru.shtamov.uisupTest.service.HeadService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HeadControllerTest {

    @Mock
    private HeadService headService;

    @Mock
    private HeadAssembler headAssembler;

    @InjectMocks
    private HeadController headController;

    private HeadCreateDTO headCreateDTO;
    private HeadGetDTO headGetDTO;
    private final String uuid = "test-uuid";

    @BeforeEach
    void setUp() {
        headCreateDTO = new HeadCreateDTO("Имейнов Именя Имнеевич");
        headGetDTO = new HeadGetDTO("test-uuid", "Имейнов Именя Имнеевич");
    }

    @Test
    void testCreate() throws IsAlreadyExistException {
        when(headService.createHead(any())).thenReturn(null);
        when(headAssembler.fromDTOToHead(headCreateDTO)).thenReturn(null);
        when(headAssembler.fromHeadToDTO(null)).thenReturn(headGetDTO);

        ResponseEntity<HeadGetDTO> response = headController.create(headCreateDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(headGetDTO, response.getBody());
    }

    @Test
    void testGet() {
        when(headService.getHead(uuid)).thenReturn(null);
        when(headAssembler.fromHeadToDTO(null)).thenReturn(headGetDTO);

        ResponseEntity<HeadGetDTO> response = headController.get(uuid);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(headGetDTO, response.getBody());
    }

    @Test
    void testUpdate() {
        when(headService.updateHead(any(), eq(uuid))).thenReturn(null);
        when(headAssembler.fromDTOToHead(headCreateDTO)).thenReturn(null);
        when(headAssembler.fromHeadToDTO(null)).thenReturn(headGetDTO);

        ResponseEntity<HeadGetDTO> response = headController.update(headCreateDTO, uuid);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(headGetDTO, response.getBody());
    }

    @Test
    void testDelete() {
        doNothing().when(headService).deleteHead(uuid);

        ResponseEntity<?> response = headController.delete(uuid);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(headService, times(1)).deleteHead(uuid);
    }
}
