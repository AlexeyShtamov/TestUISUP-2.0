package ru.shtamov.uisupTest.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shtamov.uisupTest.domain.Institute;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;
import ru.shtamov.uisupTest.extern.repositories.InstituteRepository;
import ru.shtamov.uisupTest.service.impls.InstituteServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstituteServiceImplTest {

    @Mock
    private InstituteRepository instituteRepository;

    @InjectMocks
    private InstituteServiceImpl instituteService;

    private Institute institute;
    private final String uuid = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        institute = new Institute();
        institute.setUuid(UUID.fromString(uuid));
        institute.setTitle("Test Institute");
    }

    @Test
    void testCreateInstitute() throws IsAlreadyExistException {
        when(instituteRepository.save(any())).thenReturn(institute);

        Institute created = instituteService.createInstitute(institute);

        assertNotNull(created);
        assertEquals("Test Institute", created.getTitle());
        verify(instituteRepository, times(1)).save(institute);
    }

    @Test
    void testGetInstitute() {
        when(instituteRepository.findById(UUID.fromString(uuid))).thenReturn(Optional.of(institute));

        Institute found = instituteService.getInstitute(uuid);

        assertNotNull(found);
        assertEquals("Test Institute", found.getTitle());
        verify(instituteRepository, times(1)).findById(UUID.fromString(uuid));
    }

    @Test
    void testUpdateInstitute() {
        Institute updatedInstitute = new Institute();
        updatedInstitute.setTitle("Updated Institute");

        when(instituteRepository.findById(UUID.fromString(uuid))).thenReturn(Optional.of(institute));
        when(instituteRepository.save(any())).thenReturn(updatedInstitute);

        Institute result = instituteService.updateInstitute(updatedInstitute, uuid);

        assertNotNull(result);
        assertEquals("Updated Institute", result.getTitle());
        verify(instituteRepository, times(1)).findById(UUID.fromString(uuid));
        verify(instituteRepository, times(1)).save(institute);
    }

    @Test
    void testDeleteInstitute() {
        doNothing().when(instituteRepository).deleteById(UUID.fromString(uuid));

        assertDoesNotThrow(() -> instituteService.deleteInstitute(uuid));
        verify(instituteRepository, times(1)).deleteById(UUID.fromString(uuid));
    }
}
