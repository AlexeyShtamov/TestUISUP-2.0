package ru.shtamov.uisupTest.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public enum Level {
    BACHELOR("Бакалавр"),
    APPLIED_BACHELOR("Прикладной бакалавриат"),
    SPECIALIST("Специалист"),
    MASTER("Магистр"),
    GRADUATE_STUDENT("Аспирант");

    private final String title;

    public static Optional<Level> getLevelByTitle(String title){
        return switch (title) {
            case "Бакалавр" -> Optional.of(Level.BACHELOR);
            case "Прикладной бакалавриат" -> Optional.of(Level.APPLIED_BACHELOR);
            case "Специалист" -> Optional.of(Level.SPECIALIST);
            case "Магистр" -> Optional.of(Level.MASTER);
            case "Аспирант" -> Optional.of(Level.GRADUATE_STUDENT);
            default -> Optional.empty();
        };
    }

}
