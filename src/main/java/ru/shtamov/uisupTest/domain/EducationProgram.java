package ru.shtamov.uisupTest.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import ru.shtamov.uisupTest.domain.enums.Level;
import ru.shtamov.uisupTest.domain.enums.Standard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Образовательная программа
 * Сущность, которая описывает программу подготовки, учебные планы,	программы знаний
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EducationProgram {

    /** Итендификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    /** Название */
    private String title;

    /** Шифр */
    private String cypher;

    /**
     * Уровень обучения
     * @see Level
     * */
    @Enumerated(EnumType.STRING)
    private Level level;

    /**
     * Стандард обучения
     * @see Standard
     * */
    @Enumerated(EnumType.STRING)
    private Standard standard;

    /** Институт, которому принадлежит ОП */
    @ManyToOne
    @JoinColumn(name = "institute_uuid", nullable = false)
    private Institute institute;

    /** Ответственное лицо ОП */
    @ManyToOne
    @JoinColumn(name = "head_uuid")
    private Head head;

    /** Дата следующей аккредитации*/
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate accreditationDate;

    /** Список модулей ОП */
    @ManyToMany
    @JoinTable(
            name = "program_module",
            joinColumns = @JoinColumn(name = "program_uuid"),
            inverseJoinColumns = @JoinColumn(name = "module_id")
    )
    private List<Module> modules = new ArrayList<>();



}
