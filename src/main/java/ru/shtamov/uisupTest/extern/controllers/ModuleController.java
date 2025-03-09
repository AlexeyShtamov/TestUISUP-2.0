package ru.shtamov.uisupTest.extern.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shtamov.uisupTest.extern.DTOs.module.ModuleCreateDTO;
import ru.shtamov.uisupTest.extern.DTOs.module.ModuleGetDTO;
import ru.shtamov.uisupTest.extern.assemblers.ModuleAssembler;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;
import ru.shtamov.uisupTest.service.ModuleService;

@AllArgsConstructor
@RestController
@RequestMapping("/module")
public class ModuleController {

    private final ModuleService moduleService;
    private final ModuleAssembler moduleAssembler;

    @PostMapping
    public ResponseEntity<ModuleGetDTO> create(@Valid @RequestBody ModuleCreateDTO moduleDTO) throws IllegalAccessException, IsAlreadyExistException {
        return new ResponseEntity<>(
                moduleAssembler.fromModuleToDTO(moduleService.createModule(moduleAssembler.fromDTOToModule(moduleDTO)))
                , HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ModuleGetDTO> get(@PathVariable String uuid){
        return new ResponseEntity<>(moduleAssembler.fromModuleToDTO(moduleService.getModule(uuid)), HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ModuleGetDTO> update(@Valid @RequestBody ModuleCreateDTO moduleCreateDTO, @PathVariable String uuid) throws IllegalAccessException {
        return new ResponseEntity<>(
                moduleAssembler.fromModuleToDTO(moduleService.updateModule(moduleAssembler.fromDTOToModule(moduleCreateDTO), uuid))
                , HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid){
        moduleService.deleteModule(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
