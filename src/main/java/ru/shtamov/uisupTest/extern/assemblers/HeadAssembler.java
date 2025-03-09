package ru.shtamov.uisupTest.extern.assemblers;

import org.springframework.stereotype.Component;
import ru.shtamov.uisupTest.domain.Head;
import ru.shtamov.uisupTest.extern.DTOs.head.HeadCreateDTO;
import ru.shtamov.uisupTest.extern.DTOs.head.HeadGetDTO;

@Component
public class HeadAssembler {

    public HeadGetDTO fromHeadToDTO(Head head){
        return new HeadGetDTO(head.getUuid().toString(), head.getFullName());
    }

    public Head fromDTOToHead(HeadCreateDTO headCreateDTO){
        return Head.builder().fullName(headCreateDTO.fullName()).build();
    }

}
