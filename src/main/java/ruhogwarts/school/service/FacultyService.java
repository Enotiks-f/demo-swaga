package ruhogwarts.school.service;

import org.springframework.stereotype.Service;
import ruhogwarts.school.model.Faculty;

import java.util.*;

@Service
public class FacultyService {

    private final Map<Long, Faculty> faculties = new HashMap<>();
    private Long currentId = 1L;

    public Collection<Faculty> getAllFaculties() {
        return faculties.values();
    }

    public Faculty getFaculty(Long id) {
        return faculties.get(id);
    }

    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(currentId);
        faculties.put(currentId, faculty);
        currentId++;
        return faculty;
    }

    public Faculty updateFaculty(Long id, Faculty updated) {
        Faculty faculty = faculties.get(id);

        if (faculty == null) {
            throw new RuntimeException("Faculty not found");
        }

        faculty.setName(updated.getName());
        faculty.setColor(updated.getColor());

        return faculty;
    }

    public void removeFaculty(Long id) {
        faculties.remove(id);
    }

    public Collection<Faculty> findByColor(String color) {
        List<Faculty> result = new ArrayList<>();
        for (Faculty f : faculties.values()) {
            if (f.getColor().equalsIgnoreCase(color)) {
                result.add(f);
            }
        }
        return result;
    }
}