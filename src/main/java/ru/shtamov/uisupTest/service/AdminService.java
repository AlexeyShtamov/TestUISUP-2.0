package ru.shtamov.uisupTest.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;

@Service
public interface AdminService extends UserDetailsService {

    /**
     * Метод регистрации нового админа, данные с почтой и паролей высылаются на почту.
     * @param username имя пользователя
     * @param password пароль
     * @throws IsAlreadyExistException выбрасывается, есть есть админ с таким же именем пользователя
     */
    void registerNewAdmin(String username, String password) throws IsAlreadyExistException;
}
