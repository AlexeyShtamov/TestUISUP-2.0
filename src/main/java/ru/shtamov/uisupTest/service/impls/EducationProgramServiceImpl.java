package ru.shtamov.uisupTest.service.impls;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import ru.shtamov.uisupTest.domain.EducationProgram;
import ru.shtamov.uisupTest.domain.Module;
import ru.shtamov.uisupTest.domain.enums.SortedType;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;
import ru.shtamov.uisupTest.extern.repositories.EducationProgramRepository;
import ru.shtamov.uisupTest.service.EducationProgramService;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@AllArgsConstructor
public class EducationProgramServiceImpl implements EducationProgramService {

    private final EducationProgramRepository educationProgramRepository;
    private final EducationProgramSortService educationProgramSortService;

    @Override
    public EducationProgram createEducationProgram(EducationProgram educationProgram) throws IsAlreadyExistException {
        if (educationProgramRepository.findByTitle(educationProgram.getTitle()).isPresent())
            throw new IsAlreadyExistException(String.format("EducationProgram with title %s already exist", educationProgram.getTitle()));
        EducationProgram createdEducProg = educationProgramRepository.save(educationProgram);
        log.info("EducProg {} is saved with id {}", createdEducProg.getTitle(), createdEducProg.getUuid());
        return createdEducProg;
    }

    @Override
    public EducationProgram getEducationProgram(String uuid) {
        EducationProgram foundedEducProg = educationProgramRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new NullPointerException("No EducProg with uuid " + uuid));

        log.info("EducProg with id {} is founded", uuid);
        return foundedEducProg;
    }

    @Override
    public EducationProgram updateEducationProgram(EducationProgram educationProgram, String uuid) {
        EducationProgram foundedEducProg = educationProgramRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new NullPointerException("No head with uuid " + uuid));

        if (educationProgram.getTitle() != null) foundedEducProg.setTitle(educationProgram.getTitle());
        if (educationProgram.getCypher() != null) foundedEducProg.setCypher(educationProgram.getCypher());
        if (educationProgram.getAccreditationDate() != null) foundedEducProg.setAccreditationDate(educationProgram.getAccreditationDate());
        foundedEducProg.setLevel(educationProgram.getLevel());
        foundedEducProg.setStandard(educationProgram.getStandard());
        educationProgramRepository.save(foundedEducProg);
        log.info("EducProg with id {} was updated", foundedEducProg.getUuid());

        return foundedEducProg;
    }

    @Override
    public void deleteEducationProgram(String uuid) {
        educationProgramRepository.deleteById(UUID.fromString(uuid));
        log.info("EducProg with id {} is deleted", uuid);
    }

    @Override
    public List<EducationProgram> getAllPrograms(SortedType sortedType) {

        List<EducationProgram> ep = educationProgramRepository.findAll();

        System.out.println(ep);

        return switch (sortedType){
            case BY_TITLE -> educationProgramSortService.sortByTitle(ep);
            case BY_CYPHER -> educationProgramSortService.sortByCypher(ep);
            case BY_DATE -> educationProgramSortService.sortByDate(ep);
            case BY_STANDARD -> educationProgramSortService.sortByStandard(ep);
            default -> ep;
        };
    }


    @Override
    public EducationProgram addModule(String uuid, Module module) {
        EducationProgram foundedEducProg = educationProgramRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new NullPointerException("No EducProg with uuid " + uuid));

        foundedEducProg.getModules().forEach(e -> {
            if (e.getUuid().equals(module.getUuid())){
                throw new RuntimeException(String.format(
                        "Module %s already in program %s", module.getTitle(), foundedEducProg.getTitle()));
            }
        });
        foundedEducProg.getModules().add(module);
        EducationProgram savedProgram = educationProgramRepository.save(foundedEducProg);
        log.info("Module {} added in EP {}", module.getTitle(), foundedEducProg.getTitle());
        return savedProgram;
    }


}
