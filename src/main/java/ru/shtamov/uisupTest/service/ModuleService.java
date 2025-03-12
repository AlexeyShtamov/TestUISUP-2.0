package ru.shtamov.uisupTest.service;

import org.springframework.data.domain.Page;
import ru.shtamov.uisupTest.domain.Module;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;

public interface ModuleService {

    /**
     * Метод для создания модуля
     * @param module сущность модуля
     * @return созданный модуль
     * @throws IsAlreadyExistException выбрасывается, если уже есть модуль с таким же именем
     */
    Module createModule(Module module) throws IsAlreadyExistException;

    /**
     * Метод получения модуля по итендификатору
     * @param uuid итендификатор
     * @return найденный модуль
     */
    Module getModule(String uuid);

    /**
     * Метод для получения всех модулей
     * @param offset станица (счет начинается с 0)
     * @param limit количество на странице
     * @return Страницу модулей
     */
    Page<Module> getAllModules(Integer offset, Integer limit);

    /**
     * Метод для обновленния данных модуля
     * @param module сущность модуля с обновленными данными
     * @param uuid итендфикатор
     * @return обновленный модуль
     */
    Module updateModule(Module module, String uuid);

    /**
     * Метод для удаления модуля по итендификатору
     * @param uuid итендификатор
     */
    void deleteModule(String uuid);
}
