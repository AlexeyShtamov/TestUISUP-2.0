package ru.shtamov.uisupTest.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public enum Standard {
    SUOS("СУОС"),
    SUT("СУТ"),
    FGOS_VO("ФГОС ВО"),
    FGOS_VPO("ФГОС ВПО"),
    FGOS("ФГОС 3++");

    private final String title;

    public static Optional<Standard> getStandardByTitle(String title){
        return switch (title) {
            case "СУОС" -> Optional.of(Standard.SUOS);
            case "СУТ" -> Optional.of(Standard.SUT);
            case "ФГОС ВО" -> Optional.of(Standard.FGOS_VO);
            case "ФГОС ВПО" -> Optional.of(Standard.FGOS_VPO);
            case "ФГОС 3++" -> Optional.of(Standard.FGOS);
            default -> Optional.empty();
        };
    }
}
