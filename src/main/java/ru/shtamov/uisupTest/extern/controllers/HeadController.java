package ru.shtamov.uisupTest.extern.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shtamov.uisupTest.domain.Head;
import ru.shtamov.uisupTest.domain.Institute;
import ru.shtamov.uisupTest.extern.DTOs.head.HeadCreateDTO;
import ru.shtamov.uisupTest.extern.DTOs.head.HeadGetDTO;
import ru.shtamov.uisupTest.extern.DTOs.institute.InstituteGetDTO;
import ru.shtamov.uisupTest.extern.assemblers.HeadAssembler;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;
import ru.shtamov.uisupTest.service.HeadService;

@AllArgsConstructor
@RestController
@RequestMapping("/head")
public class HeadController {

    private final HeadService headService;
    private final HeadAssembler headAssembler;

    @PostMapping
    public ResponseEntity<HeadGetDTO> create(@Valid @RequestBody HeadCreateDTO headDTO) throws IsAlreadyExistException {
        return new ResponseEntity<>(
                headAssembler.fromHeadToDTO(headService.createHead(headAssembler.fromDTOToHead(headDTO)))
                , HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<HeadGetDTO> get(@PathVariable String uuid){
        return new ResponseEntity<>(headAssembler.fromHeadToDTO(headService.getHead(uuid)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<HeadGetDTO>> getAll(@RequestParam("offset")  Integer offset,
                                                        @RequestParam("limit") Integer limit) {

        Page<Head> headPage = headService.getAllHeads(offset, limit);
        Page<HeadGetDTO> dtoPage = headPage.map(headAssembler::fromHeadToDTO);

        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<HeadGetDTO> update(@Valid @RequestBody HeadCreateDTO headDTO, @PathVariable String uuid){
        return new ResponseEntity<>(
                headAssembler.fromHeadToDTO(headService.updateHead(headAssembler.fromDTOToHead(headDTO), uuid))
                , HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid){
        headService.deleteHead(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
