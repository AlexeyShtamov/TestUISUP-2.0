package ru.shtamov.uisupTest.domain;

import jakarta.persistence.*;
import lombok.*;
import ru.shtamov.uisupTest.domain.enums.ModuleType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Модуль образовательной программы.
 * Модуль может принадлежать нескольким ОП
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Module {

    /** Итендификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    /** Название */
    private String title;

    /**
     * Тип модуля
     * @see ModuleType
     */
    @Enumerated(EnumType.STRING)
    private ModuleType moduleType;

    /** Список ОП, в которые входит данный модуль*/
    @ManyToMany(mappedBy = "modules")
    private List<EducationProgram> educationPrograms = new ArrayList<>();
}
