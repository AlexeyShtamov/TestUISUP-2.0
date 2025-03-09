package ru.shtamov.uisupTest.service.impls;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.shtamov.uisupTest.domain.Module;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;
import ru.shtamov.uisupTest.extern.repositories.ModuleRepository;
import ru.shtamov.uisupTest.service.ModuleService;

import java.util.UUID;

@Log4j2
@AllArgsConstructor
@Service
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;

    @Override
    public Module createModule(Module module) throws IsAlreadyExistException {
        if (moduleRepository.findByTitle(module.getTitle()).isPresent())
            throw new IsAlreadyExistException(String.format("Module with title %s already exist", module.getTitle()));
        Module createdModule = moduleRepository.save(module);
        log.info("Module {} is saved with id {}", createdModule.getTitle(), createdModule.getUuid());
        return createdModule;
    }

    @Override
    public Module getModule(String uuid) {
        Module foundedModule = moduleRepository.findById(java.util.UUID.fromString(uuid))
                .orElseThrow(() -> new NullPointerException("No Module with uuid " + uuid));

        log.info("Module with id {} is founded", uuid);
        return foundedModule;
    }

    @Override
    public Module updateModule(Module module, String uuid) {
        Module foundedModule = moduleRepository.findById(java.util.UUID.fromString(uuid))
                .orElseThrow(() -> new NullPointerException("No Module with uuid " + uuid));

        if (module.getTitle() != null) foundedModule.setTitle(module.getTitle());
        foundedModule.setModuleType(module.getModuleType());
        moduleRepository.save(foundedModule);
        log.info("Module with id {} was updated", foundedModule.getUuid());

        return foundedModule;
    }

    @Override
    public void deleteModule(String uuid) {
        moduleRepository.deleteById(UUID.fromString(uuid));
        log.info("Module with id {} is deleted", uuid);
    }
}
