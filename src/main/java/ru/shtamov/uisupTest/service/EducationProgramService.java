package ru.shtamov.uisupTest.service;

import ru.shtamov.uisupTest.domain.EducationProgram;
import ru.shtamov.uisupTest.domain.Module;
import ru.shtamov.uisupTest.domain.enums.SortedType;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;

import java.util.List;

public interface EducationProgramService {

    /**
     * Метод создание ОП
     * @param educationProgram сущность ОП
     * @return созданную ОП
     * @throws IsAlreadyExistException выбрасывается, если есть ОП с таким же именем
     */
    EducationProgram createEducationProgram(EducationProgram educationProgram) throws IsAlreadyExistException;

    /**
     * Метод получение ОП по итендификатору
     * @param uuid итендификатор
     * @return найденная ОП
     */
    EducationProgram getEducationProgram(String uuid);

    /**
     * Метод обновления ОП
     * @param educationProgram сущность с обновленными данными
     * @param uuid итендификатор
     * @return обновленную сущность
     */
    EducationProgram updateEducationProgram(EducationProgram educationProgram, String uuid);

    /**
     * Метод для удаления ОП
     * @param uuid итендификатор
     */
    void deleteEducationProgram(String uuid);

    /**
     * Метод для получения всех ОП, используя соритировку при необходимости
     * @param sortedType Тип сортировки
     * @return Список ОП
     */
    List<EducationProgram> getAllPrograms(SortedType sortedType);

    /**
     * Метод добавления нового модуля в ОП
     * @param uuid итендификатор
     * @param module модуль
     * @return ОП с добавленным модулем
     */
    EducationProgram addModule(String uuid, Module module);


}
