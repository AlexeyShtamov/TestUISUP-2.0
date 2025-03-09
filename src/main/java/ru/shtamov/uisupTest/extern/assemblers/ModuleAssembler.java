package ru.shtamov.uisupTest.extern.assemblers;

import org.springframework.stereotype.Component;
import ru.shtamov.uisupTest.domain.Module;
import ru.shtamov.uisupTest.domain.enums.ModuleType;
import ru.shtamov.uisupTest.extern.DTOs.educProg.EducProgInnerDTO;
import ru.shtamov.uisupTest.extern.DTOs.module.ModuleCreateDTO;
import ru.shtamov.uisupTest.extern.DTOs.module.ModuleGetDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModuleAssembler {
    public ModuleGetDTO fromModuleToDTO(Module module) {

        List<EducProgInnerDTO> innerDTOList = new ArrayList<>();
        if (module.getEducationPrograms() != null)
            innerDTOList = module.getEducationPrograms()
                    .stream().map(e -> new EducProgInnerDTO(e.getUuid().toString(), e.getTitle())).toList();

        return new ModuleGetDTO(module.getUuid().toString(), module.getTitle(), module.getModuleType().toString(), innerDTOList);

    }

    public Module fromDTOToModule(ModuleCreateDTO moduleDTO) throws IllegalArgumentException {


        ModuleType moduleType;
        try {
            moduleType = ModuleType.valueOf(moduleDTO.moduleType().toUpperCase());
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("No module with type: " + moduleDTO.moduleType());
        }

        return Module.builder().title(moduleDTO.title()).moduleType(moduleType).build();
    }
}
