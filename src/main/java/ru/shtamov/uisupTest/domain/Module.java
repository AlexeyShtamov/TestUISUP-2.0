package ru.shtamov.uisupTest.domain;

import jakarta.persistence.*;
import lombok.*;
import ru.shtamov.uisupTest.domain.enums.ModuleType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private String title;

    @Enumerated(EnumType.STRING)
    private ModuleType moduleType;

    @ManyToMany(mappedBy = "modules")
    private List<EducationProgram> educationPrograms = new ArrayList<>();
}
