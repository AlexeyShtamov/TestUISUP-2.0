package ru.shtamov.uisupTest.service.runners;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.shtamov.uisupTest.domain.Admin;
import ru.shtamov.uisupTest.extern.repositories.AdminRepository;

@Component
@Log4j2
public class AdminRunner implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminRunner(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args){
        if (adminRepository.count() == 0){
            Admin admin = Admin.builder().
                    username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword))
                    .build();

            adminRepository.save(admin);
            log.info("Admin with username {} is saved", adminUsername);
        }
    }
}
