package ru.shtamov.uisupTest.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Институт
 * Институт может включать в себя несколько ОП
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Institute {

    /** Итендификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    /** Название */
    private String title;

    /** Список ОП, входящих в институт*/
    @OneToMany(mappedBy = "institute", cascade = CascadeType.PERSIST)
    private List<EducationProgram> educationPrograms = new ArrayList<>();
}
