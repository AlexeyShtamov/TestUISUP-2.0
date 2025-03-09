package ru.shtamov.uisupTest.service;

import ru.shtamov.uisupTest.domain.EducationProgram;
import ru.shtamov.uisupTest.domain.Institute;
import ru.shtamov.uisupTest.domain.Module;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;

public interface InstituteService {

    /**
     * Метод для создания института
     * @param institute сущность института
     * @return созданный институт
     * @throws IsAlreadyExistException выбрасывается, если уже есть институт с таким же именем
     */
    Institute createInstitute(Institute institute) throws IsAlreadyExistException;

    /**
     * Метод получения института по итендификатору
     * @param uuid итендификатор
     * @return найденный институт
     */
    Institute getInstitute(String uuid);

    /**
     * Метод для обновленния данных института
     * @param institute сущность института с обновленными данными
     * @param uuid итендфикатор
     * @return обновленный иститут
     */
    Institute updateInstitute(Institute institute, String uuid);

    /**
     * Метод для удаления института по итендификатору
     * @param uuid итендификатор
     */
    void deleteInstitute(String uuid);

    /**
     * Метод добавления новой ОП в институт
     * @param uuid итендификатор
     * @param educationProgram ОП
     * @return институт с добавленной ОП
     */
    Institute addEducationProgram(String uuid, EducationProgram educationProgram);

}
