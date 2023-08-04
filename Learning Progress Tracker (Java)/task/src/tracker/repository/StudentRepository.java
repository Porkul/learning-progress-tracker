package tracker.repository;

import lombok.Getter;
import lombok.Setter;
import tracker.model.Student;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
@Setter
@Getter
public class StudentRepository {
    Set<Student> students = new LinkedHashSet<>();
    public Optional<Student> findStudentById(String id) {
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst();
    }

}
