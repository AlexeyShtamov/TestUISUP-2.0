package ru.shtamov.uisupTest.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shtamov.uisupTest.domain.EducationProgram;
import ru.shtamov.uisupTest.domain.enums.SortedType;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;
import ru.shtamov.uisupTest.extern.repositories.EducationProgramRepository;
import ru.shtamov.uisupTest.service.impls.EducationProgramServiceImpl;
import ru.shtamov.uisupTest.service.impls.EducationProgramSortService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EducationProgramServiceTest {

    @Mock
    private EducationProgramRepository educationProgramRepository;

    @Mock
    private EducationProgramSortService educationProgramSortService;

    @InjectMocks
    private EducationProgramServiceImpl educationProgramService;

    private EducationProgram educationProgram;
    private final String uuid = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        educationProgram = new EducationProgram();
        educationProgram.setUuid(UUID.fromString(uuid));
        educationProgram.setTitle("Test Program");
    }

    @Test
    void testCreateEducationProgram() throws IsAlreadyExistException {
        when(educationProgramRepository.save(any())).thenReturn(educationProgram);
        EducationProgram result = educationProgramService.createEducationProgram(educationProgram);
        assertNotNull(result);
        assertEquals("Test Program", result.getTitle());
    }

    @Test
    void testGetEducationProgram() {
        when(educationProgramRepository.findById(UUID.fromString(uuid))).thenReturn(Optional.of(educationProgram));
        EducationProgram result = educationProgramService.getEducationProgram(uuid);
        assertNotNull(result);
        assertEquals(uuid, result.getUuid().toString());
    }

    @Test
    void testUpdateEducationProgram() {
        when(educationProgramRepository.findById(UUID.fromString(uuid))).thenReturn(Optional.of(educationProgram));
        when(educationProgramRepository.save(any())).thenReturn(educationProgram);
        EducationProgram updated = educationProgramService.updateEducationProgram(educationProgram, uuid);
        assertNotNull(updated);
    }

    @Test
    void testDeleteEducationProgram() {
        doNothing().when(educationProgramRepository).deleteById(UUID.fromString(uuid));
        educationProgramService.deleteEducationProgram(uuid);
        verify(educationProgramRepository, times(1)).deleteById(UUID.fromString(uuid));
    }

    @Test
    void testGetAllPrograms() {
        when(educationProgramRepository.findAll()).thenReturn(List.of(educationProgram));

        List<EducationProgram> result = educationProgramService.getAllPrograms(SortedType.NO_SORTED);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}
