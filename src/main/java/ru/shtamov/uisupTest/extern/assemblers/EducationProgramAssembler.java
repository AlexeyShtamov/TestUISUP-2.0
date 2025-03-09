package ru.shtamov.uisupTest.extern.assemblers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.shtamov.uisupTest.domain.EducationProgram;
import ru.shtamov.uisupTest.domain.Head;
import ru.shtamov.uisupTest.domain.Institute;
import ru.shtamov.uisupTest.domain.enums.Level;
import ru.shtamov.uisupTest.domain.enums.Standard;
import ru.shtamov.uisupTest.extern.DTOs.educProg.EducProgCreateDTO;
import ru.shtamov.uisupTest.extern.DTOs.educProg.EducProgGetDTO;
import ru.shtamov.uisupTest.extern.DTOs.educProg.EducProgUpdateDTO;
import ru.shtamov.uisupTest.extern.DTOs.head.HeadGetDTO;
import ru.shtamov.uisupTest.extern.DTOs.institute.InstituteInnerDTO;
import ru.shtamov.uisupTest.service.HeadService;
import ru.shtamov.uisupTest.service.InstituteService;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class EducationProgramAssembler {

    private final HeadService headService;
    private final InstituteService instituteService;

    public EducationProgram fromDTOToEducProg(EducProgCreateDTO educProgDTO){

        Level level = Level.getLevelByTitle(educProgDTO.level())
                .orElseThrow(() -> new IllegalArgumentException("No level with title " + educProgDTO.level()));
        Standard standard = Standard.getStandardByTitle(educProgDTO.standard())
                .orElseThrow(() -> new IllegalArgumentException("No standard with title " + educProgDTO.standard()));

        int year = Integer.parseInt(educProgDTO.accreditationDate().split("-")[0]);
        int month = Integer.parseInt(educProgDTO.accreditationDate().split("-")[1]);
        int day = Integer.parseInt(educProgDTO.accreditationDate().split("-")[2]);

        Head head = headService.getHead(educProgDTO.headUuid());
        Institute institute = instituteService.getInstitute(educProgDTO.instituteUuid());

        return EducationProgram
                        .builder()
                        .title(educProgDTO.title())
                        .cypher(educProgDTO.cypher())
                        .level(level)
                        .standard(standard)
                        .accreditationDate(LocalDate.of(year, month, day))
                        .head(head)
                        .institute(institute)
                        .build();

    }

    public EducationProgram fromDTOToEducProgUpdate(EducProgUpdateDTO educProgDTO){

        Level level = Level.getLevelByTitle(educProgDTO.level())
                .orElseThrow(() -> new IllegalArgumentException("No level with title " + educProgDTO.level()));
        Standard standard = Standard.getStandardByTitle(educProgDTO.standard())
                .orElseThrow(() -> new IllegalArgumentException("No standard with title " + educProgDTO.standard()));

        int year = Integer.parseInt(educProgDTO.accreditationDate().split("-")[0]);
        int month = Integer.parseInt(educProgDTO.accreditationDate().split("-")[1]);
        int day = Integer.parseInt(educProgDTO.accreditationDate().split("-")[2]);


        return EducationProgram
                .builder()
                .title(educProgDTO.title())
                .cypher(educProgDTO.cypher())
                .level(level)
                .standard(standard)
                .accreditationDate(LocalDate.of(year, month, day))
                .build();

    }

    public EducProgGetDTO fromEducProgToDTO(EducationProgram educProg){

            return new EducProgGetDTO(educProg.getUuid().toString(),
                    educProg.getTitle(),
                    educProg.getCypher(),
                    educProg.getLevel().getTitle(),
                    educProg.getStandard().getTitle(),
                    new InstituteInnerDTO(educProg.getInstitute().getUuid().toString(), educProg.getInstitute().getTitle()),
                    new HeadGetDTO(educProg.getHead().getUuid().toString(), educProg.getHead().getFullName()),
                    educProg.getAccreditationDate());
    }
}
