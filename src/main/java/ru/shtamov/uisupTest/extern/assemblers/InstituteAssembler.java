package ru.shtamov.uisupTest.extern.assemblers;

import org.springframework.stereotype.Component;
import ru.shtamov.uisupTest.domain.Institute;
import ru.shtamov.uisupTest.extern.DTOs.educProg.EducProgInnerDTO;
import ru.shtamov.uisupTest.extern.DTOs.institute.InstituteCreateDTO;
import ru.shtamov.uisupTest.extern.DTOs.institute.InstituteGetDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstituteAssembler {

    public InstituteGetDTO fromInstituteToDTO(Institute institute) {
        List<EducProgInnerDTO> innerDTOList = new ArrayList<>();

        if (institute.getEducationPrograms() != null)
            innerDTOList = institute.getEducationPrograms()
                .stream().map(e -> new EducProgInnerDTO(e.getUuid().toString(), e.getTitle())).toList();

        return new InstituteGetDTO(institute.getUuid().toString(), institute.getTitle(), innerDTOList);
    }

    public Institute fromDTOToInstitute(InstituteCreateDTO instituteDTO) {
        return Institute.builder().title(instituteDTO.title()).build();
    }
}
