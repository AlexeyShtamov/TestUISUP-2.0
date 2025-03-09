package ru.shtamov.uisupTest.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shtamov.uisupTest.domain.Head;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;
import ru.shtamov.uisupTest.extern.repositories.HeadRepository;
import ru.shtamov.uisupTest.service.impls.HeadServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HeadServiceImplTest {

    @Mock
    private HeadRepository headRepository;

    @InjectMocks
    private HeadServiceImpl headService;

    private Head head;
    private final String uuid = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        head = new Head();
        head.setUuid(UUID.fromString(uuid));
        head.setFullName("John Doe");
    }

    @Test
    void testCreateHead() throws IsAlreadyExistException {
        when(headRepository.save(any(Head.class))).thenReturn(head);

        Head createdHead = headService.createHead(head);

        assertNotNull(createdHead);
        assertEquals("John Doe", createdHead.getFullName());
        verify(headRepository, times(1)).save(head);
    }

    @Test
    void testGetHead() {
        when(headRepository.findById(UUID.fromString(uuid))).thenReturn(Optional.of(head));

        Head foundHead = headService.getHead(uuid);

        assertNotNull(foundHead);
        assertEquals(uuid, foundHead.getUuid().toString());
        verify(headRepository, times(1)).findById(UUID.fromString(uuid));
    }

    @Test
    void testGetHead_NotFound() {
        when(headRepository.findById(UUID.fromString(uuid))).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> headService.getHead(uuid));
        verify(headRepository, times(1)).findById(UUID.fromString(uuid));
    }

    @Test
    void testUpdateHead() {
        Head updatedHead = new Head();
        updatedHead.setFullName("Jane Doe");

        when(headRepository.findById(UUID.fromString(uuid))).thenReturn(Optional.of(head));
        when(headRepository.save(any(Head.class))).thenReturn(updatedHead);

        Head result = headService.updateHead(updatedHead, uuid);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getFullName());
        verify(headRepository, times(1)).findById(UUID.fromString(uuid));
        verify(headRepository, times(1)).save(any(Head.class));
    }

    @Test
    void testDeleteHead() {
        doNothing().when(headRepository).deleteById(UUID.fromString(uuid));

        headService.deleteHead(uuid);

        verify(headRepository, times(1)).deleteById(UUID.fromString(uuid));
    }
}
