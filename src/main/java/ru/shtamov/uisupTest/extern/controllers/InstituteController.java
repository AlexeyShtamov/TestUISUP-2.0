package ru.shtamov.uisupTest.extern.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shtamov.uisupTest.domain.EducationProgram;
import ru.shtamov.uisupTest.domain.Institute;
import ru.shtamov.uisupTest.domain.Module;
import ru.shtamov.uisupTest.extern.DTOs.educProg.EducProgGetDTO;
import ru.shtamov.uisupTest.extern.DTOs.institute.InstituteCreateDTO;
import ru.shtamov.uisupTest.extern.DTOs.institute.InstituteGetDTO;
import ru.shtamov.uisupTest.extern.DTOs.module.ModuleGetDTO;
import ru.shtamov.uisupTest.extern.assemblers.InstituteAssembler;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;
import ru.shtamov.uisupTest.service.EducationProgramService;
import ru.shtamov.uisupTest.service.InstituteService;

@AllArgsConstructor
@RestController
@RequestMapping("/institute")
public class InstituteController {

    private final InstituteService instituteService;
    private final InstituteAssembler instituteAssembler;
    private final EducationProgramService educationProgramService;

    @PostMapping
    public ResponseEntity<InstituteGetDTO> create(@Valid @RequestBody InstituteCreateDTO instituteDTO) throws IsAlreadyExistException {
        return new ResponseEntity<>(
                instituteAssembler.fromInstituteToDTO(instituteService.createInstitute(instituteAssembler.fromDTOToInstitute(instituteDTO)))
                , HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<InstituteGetDTO> get(@PathVariable String uuid){
        return new ResponseEntity<>(instituteAssembler.fromInstituteToDTO(instituteService.getInstitute(uuid)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<InstituteGetDTO>> getAll(@RequestParam("offset")  Integer offset,
                                                     @RequestParam("limit") Integer limit) {

        Page<Institute> institutePage = instituteService.getAllInstitutes(offset, limit);
        Page<InstituteGetDTO> dtoPage = institutePage.map(instituteAssembler::fromInstituteToDTO);

        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<InstituteGetDTO> update(@Valid @RequestBody InstituteCreateDTO instituteDTO, @PathVariable String uuid){
        return new ResponseEntity<>(
                instituteAssembler.fromInstituteToDTO(instituteService.updateInstitute(instituteAssembler.fromDTOToInstitute(instituteDTO), uuid))
                , HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid){
        instituteService.deleteInstitute(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Метод добавления нового ОП к институту"
    )
    @PutMapping("/{uuid}/{prog-uuid}")
    public ResponseEntity<InstituteGetDTO> addEducProg(@PathVariable String uuid, @PathVariable(name = "prog-uuid") @Parameter(description = "UUID ОП ", example = "bc05f6c4-928a-4a67-8bcb-d8b2be36b4e1") String progUuid){
        EducationProgram educationProgram = educationProgramService.getEducationProgram(progUuid);
        Institute institute = instituteService.addEducationProgram(uuid, educationProgram);
        return new ResponseEntity<>(instituteAssembler.fromInstituteToDTO(institute), HttpStatus.OK);
    }
}
