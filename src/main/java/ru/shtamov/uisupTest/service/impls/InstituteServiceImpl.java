package ru.shtamov.uisupTest.service.impls;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.shtamov.uisupTest.domain.EducationProgram;
import ru.shtamov.uisupTest.domain.Institute;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;
import ru.shtamov.uisupTest.extern.repositories.InstituteRepository;
import ru.shtamov.uisupTest.service.InstituteService;

import java.util.UUID;

@Log4j2
@AllArgsConstructor
@Service
public class InstituteServiceImpl implements InstituteService {

    private final InstituteRepository instituteRepository;

    @Override
    public Institute createInstitute(Institute institute) throws IsAlreadyExistException {
        if (instituteRepository.findByTitle(institute.getTitle()).isPresent())
            throw new IsAlreadyExistException(String.format("Institute with title %s already exist", institute.getTitle()));
        Institute createdInstitute = instituteRepository.save(institute);
        log.info("Institute {} is saved with id {}", createdInstitute.getTitle(), createdInstitute.getUuid());
        return createdInstitute;
    }

    @Override
    public Institute getInstitute(String uuid) {
        Institute foundedInstitute = instituteRepository.findById(java.util.UUID.fromString(uuid))
                .orElseThrow(() -> new NullPointerException("No Institute with uuid " + uuid));

        log.info("Institute with id {} is founded", uuid);
        return foundedInstitute;
    }

    @Override
    public Institute updateInstitute(Institute institute, String uuid) {
        Institute foundedInstitute = instituteRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new NullPointerException("No Institute with uuid " + uuid));

        if (!foundedInstitute.getTitle().equals(institute.getTitle()) && institute.getTitle() != null){
            foundedInstitute.setTitle(institute.getTitle());
            instituteRepository.save(foundedInstitute);
            log.info("Institute's title was updated on {}", institute.getTitle());
        }
        else log.warn("Institute's new title was empty or same, name: {}", institute.getTitle());

        return foundedInstitute;
    }

    @Override
    public void deleteInstitute(String uuid) {
        instituteRepository.deleteById(UUID.fromString(uuid));
        log.info("Institute with id {} is deleted", uuid);
    }

    @Override
    public Institute addEducationProgram(String uuid, EducationProgram educationProgram) {
        Institute foundedInstitute = instituteRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new NullPointerException("No Institute with uuid " + uuid));

        foundedInstitute.getEducationPrograms().forEach(e -> {
            if (e.getUuid().equals(educationProgram.getUuid())){
                throw new RuntimeException(String.format(
                        "EP %s already in institute %s", educationProgram.getTitle(), foundedInstitute.getTitle()));
            }
        });
        foundedInstitute.getEducationPrograms().add(educationProgram);
        Institute savedInstitute = instituteRepository.save(foundedInstitute);
        log.info("EP {} added in EP {}", educationProgram.getTitle(), foundedInstitute.getTitle());
        return savedInstitute;
    }
}
