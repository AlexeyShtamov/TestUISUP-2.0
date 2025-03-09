package ru.shtamov.uisupTest.service.impls;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.shtamov.uisupTest.domain.Head;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;
import ru.shtamov.uisupTest.extern.repositories.HeadRepository;
import ru.shtamov.uisupTest.service.HeadService;

import java.util.UUID;

@Log4j2
@Service
@AllArgsConstructor
public class HeadServiceImpl implements HeadService {

    private HeadRepository headRepository;

    @Override
    public Head createHead(Head head) throws IsAlreadyExistException {
        if (headRepository.findByFullName(head.getFullName()).isPresent())
            throw new IsAlreadyExistException(String.format("Head with name %s already exist", head.getFullName()));
        Head createdHead = headRepository.save(head);
        log.info("Head {} is saved with id {}", createdHead.getFullName(), createdHead.getUuid());
        return createdHead;
    }

    @Override
    public Head getHead(String uuid) {
        Head foundedHead = headRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new NullPointerException("No head with uuid " + uuid));

        log.info("Head with id {} is founded", uuid);
        return foundedHead;
    }

    @Override
    public Head updateHead(Head head, String uuid) {
        Head foundedHead = headRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new NullPointerException("No head with uuid " + uuid));

        if (!foundedHead.getFullName().equals(head.getFullName()) && head.getFullName() != null){
            foundedHead.setFullName(head.getFullName());
            headRepository.save(foundedHead);
            log.info("Head's name was updated on {}", head.getFullName());
        }
        else log.warn("Head's new name was empty or same, name: {}", head.getFullName());

        return foundedHead;
    }

    @Override
    public void deleteHead(String uuid) {
        headRepository.deleteById(UUID.fromString(uuid));
        log.info("Head with id {} is deleted", uuid);
    }
}
