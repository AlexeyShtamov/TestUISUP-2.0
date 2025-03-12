package ru.shtamov.uisupTest.service;

import org.springframework.data.domain.Page;
import ru.shtamov.uisupTest.domain.Head;
import ru.shtamov.uisupTest.extern.exceptions.IsAlreadyExistException;

public interface HeadService {

    /**
     * Метод создания ответственного лица
     * @param head сущность ответственного лица
     * @return созданное ОЛ
     * @throws IsAlreadyExistException выбрасывается, если уже есть ОЛ с таким же именем
     */
    Head createHead(Head head) throws IsAlreadyExistException;

    /**
     * Метод для получения ОЛ по итендификатору
     * @param uuid итендификатор
     * @return найденное ОЛ
     */
    Head getHead(String uuid);

    /**
     * Метод для получения всех ОЛ
     * @param offset станица (счет начинается с 0)
     * @param limit количество на странице
     * @return Страницу ОЛ
     */
    Page<Head> getAllHeads(Integer offset, Integer limit);

    /**
     * Метод для обновления данных у ОЛ
     * @param head ОЛ с обновленными данными
     * @param uuid итендификатор
     * @return обновленное ОЛ
     */
    Head updateHead(Head head, String uuid);

    /**
     * Метод для удаления ОЛ по итендификатору
     * @param uuid итендификатор
     */
    void deleteHead(String uuid);
}
