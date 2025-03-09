package ru.shtamov.uisupTest.service.impls;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.shtamov.uisupTest.domain.EducationProgram;

import java.util.*;

@Log4j2
@Component
public class EducationProgramSortService {

    public List<EducationProgram> sortByTitle(List<EducationProgram> programs) {
        int n = programs.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (programs.get(j).getTitle().compareTo(programs.get(j + 1).getTitle()) > 0) {
                    Collections.swap(programs, j, j + 1);
                }
            }
        }
        log.info("Programs is sorted by title");
        return programs;
    }

    public List<EducationProgram> sortByCypher(List<EducationProgram> programs) {
        int n = programs.size();

        for (int i = 1; i < n; i++) {
            EducationProgram key = programs.get(i);
            int j = i - 1;
            while (j >= 0 && programs.get(j).getCypher().compareTo(key.getCypher()) > 0) {
                programs.set(j + 1, programs.get(j));
                j = j - 1;
            }
            programs.set(j + 1, key);
        }
        log.info("Programs is sorted by cypher");
        return programs;
    }

    public List<EducationProgram> sortByDate(List<EducationProgram> programs) {
        int n = programs.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (programs.get(j).getAccreditationDate().isAfter(programs.get(j + 1).getAccreditationDate())) {
                    Collections.swap(programs, j, j + 1);
                }
            }
        }
        log.info("Programs is sorted by date");
        return programs;
    }

    public List<EducationProgram> sortByStandard(List<EducationProgram> programs) {
        int n = programs.size();

        for (int i = 1; i < n; i++) {
            EducationProgram key = programs.get(i);
            int j = i - 1;
            while (j >= 0 && programs.get(j).getStandard().compareTo(key.getStandard()) > 0) {
                programs.set(j + 1, programs.get(j));
                j = j - 1;
            }
            programs.set(j + 1, key);
        }
        log.info("Programs is sorted by standard");
        return programs;
    }

}

