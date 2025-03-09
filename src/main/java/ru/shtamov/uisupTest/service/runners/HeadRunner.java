package ru.shtamov.uisupTest.service.runners;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.shtamov.uisupTest.domain.Head;
import ru.shtamov.uisupTest.domain.Institute;
import ru.shtamov.uisupTest.extern.repositories.HeadRepository;

@Component
@AllArgsConstructor
public class HeadRunner implements CommandLineRunner {

    private final HeadRepository headRepository;

    @Override
    public void run(String... args) throws Exception {
        if (headRepository.count() == 0){

            Head head1 = Head.builder()
            .fullName("Обабков Илья Николаевич")
            .build();
            headRepository.save(head1);

            Head head2 = Head.builder()
                    .fullName("Дмитрий Витальевич Бугров")
                    .build();
            headRepository.save(head2);

            Head head3 = Head.builder()
                    .fullName("Толмачев Дмитрий Евгеньевич")
                    .build();
            headRepository.save(head3);

        }
    }
}
