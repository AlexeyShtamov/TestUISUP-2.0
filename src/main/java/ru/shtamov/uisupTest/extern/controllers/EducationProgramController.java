package ru.shtamov.uisupTest.extern.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shtamov.uisupTest.domain.EducationProgram;
import ru.shtamov.uisupTest.domain.Module;
import ru.shtamov.uisupTest.domain.enums.SortedType;
import ru.shtamov.uisupTest.extern.DTOs.educProg.EducProgCreateDTO;
import ru.shtamov.uisupTest.extern.DTOs.educProg.EducProgGetDTO;
import ru.shtamov.uisupTest.extern.DTOs.educProg.EducProgUpdateDTO;
import ru.shtamov.uisupTest.extern.assemblers.EducationProgramAssembler;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;
import ru.shtamov.uisupTest.service.EducationProgramService;
import ru.shtamov.uisupTest.service.ModuleService;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/educ_prog")
public class EducationProgramController {

    private final EducationProgramService educProgService;
    private final EducationProgramAssembler educProgAssembler;
    private final ModuleService moduleService;

    @PostMapping
    public ResponseEntity<EducProgGetDTO> create(@Valid @RequestBody EducProgCreateDTO educProgDTO) throws IsAlreadyExistException {
        return new ResponseEntity<>(
                educProgAssembler.fromEducProgToDTO(educProgService.createEducationProgram(educProgAssembler.fromDTOToEducProg(educProgDTO)))
                , HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<EducProgGetDTO> get(@PathVariable String uuid){
        return new ResponseEntity<>(educProgAssembler.fromEducProgToDTO(educProgService.getEducationProgram(uuid)), HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<EducProgGetDTO> update(@Valid @RequestBody EducProgUpdateDTO educProgDTO, @PathVariable String uuid){
        return new ResponseEntity<>(
                educProgAssembler.fromEducProgToDTO(educProgService.updateEducationProgram(educProgAssembler.fromDTOToEducProgUpdate(educProgDTO), uuid))
                , HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid){
        educProgService.deleteEducationProgram(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Метод получения всех ОП",
            description = "Метод где в качестве параметра sortedType указывается способ сортировки: BY_TITLE, BY_CYPHER, BY_DATE, BY_STANDARD, NO_SORTED"
    )
    @GetMapping("/all/{sortedType}")
    public ResponseEntity<List<EducProgGetDTO>> getAll(@PathVariable @Parameter(description = "Способ сортировки", example = "BY_TITLE") String sortedType){
        List<EducProgGetDTO> educProgGetDTOList = educProgService.getAllPrograms(SortedType.valueOf(sortedType.toUpperCase()))
                .stream().map(educProgAssembler::fromEducProgToDTO).toList();
        System.out.println(educProgGetDTOList);
        return new ResponseEntity<>(educProgGetDTOList, HttpStatus.OK);
    }

    @Operation(
            summary = "Метод добавления нового модуля к ОП"
    )
    @PutMapping("/{uuid}/{module-uuid}")
    public ResponseEntity<EducProgGetDTO> addModule(@PathVariable String uuid, @PathVariable(name = "module-uuid") @Parameter(description = "UUID модуля ", example = "bc05f6c4-928a-4a67-8bcb-d8b2be36b4e1") String moduleUuid){
        Module module = moduleService.getModule(moduleUuid);
        EducationProgram educationProgram = educProgService.addModule(uuid, module);
        return new ResponseEntity<>(educProgAssembler.fromEducProgToDTO(educationProgram), HttpStatus.OK);
    }
}
