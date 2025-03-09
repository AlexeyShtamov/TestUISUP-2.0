package ru.shtamov.uisupTest.service.runners;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.shtamov.uisupTest.domain.Head;
import ru.shtamov.uisupTest.domain.Institute;
import ru.shtamov.uisupTest.extern.repositories.InstituteRepository;
import ru.shtamov.uisupTest.service.InstituteService;

@AllArgsConstructor
@Component
public class InstituteRunner implements CommandLineRunner {

    private final InstituteRepository instituteRepository;

    @Override
    public void run(String... args) throws Exception {
        if (instituteRepository.count() == 0){
            Institute institute1 = Institute.builder()
                    .title("Институт радиоэлектроники и информационных технологий-РТФ")
                    .build();
            instituteRepository.save(institute1);

            Institute institute2 = Institute.builder()
                    .title("Уральский гуманитарный институт")
                    .build();
            instituteRepository.save(institute2);

            Institute institute3 = Institute.builder()
                    .title("Институт экономики и управления")
                    .build();
            instituteRepository.save(institute3);

        }
    }
}
