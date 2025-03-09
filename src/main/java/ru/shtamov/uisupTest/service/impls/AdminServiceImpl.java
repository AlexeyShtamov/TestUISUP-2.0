package ru.shtamov.uisupTest.service.impls;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shtamov.uisupTest.domain.Admin;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;
import ru.shtamov.uisupTest.extern.repositories.AdminRepository;
import ru.shtamov.uisupTest.service.AdminService;

@Log4j2
@Service
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByUsername(username)
                .orElseThrow(() -> new NullPointerException("No admin with username: " + username));
    }

    @Override
    public void registerNewAdmin(String username, String password) throws IsAlreadyExistException {
        if (adminRepository.findByUsername(username).isPresent())
            throw new IsAlreadyExistException(String.format("Admin with username %s already exist", username));

        adminRepository.save(Admin.builder().username(username).password(passwordEncoder.encode(password)).build());
        log.info("New admin is saved");
    }
}
