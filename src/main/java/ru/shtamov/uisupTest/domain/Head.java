package ru.shtamov.uisupTest.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Ответственное лицо, отвечающее за ОП
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Head {

    /** Итендификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    /** Полное имя */
    private String fullName;

    /** Список ОП ответственного лица*/
    @OneToMany(mappedBy = "head", cascade = CascadeType.PERSIST)
    private List<EducationProgram> educationPrograms = new ArrayList<>();
}
