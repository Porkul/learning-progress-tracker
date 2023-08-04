package tracker.dao;

import lombok.Getter;
import lombok.Setter;
import tracker.model.Student;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Setter
@Getter
public class StudentDaoImpl implements StudentDao{
    private Set<Student> students = new LinkedHashSet<>();
    @Override
    public Optional<Student> getStudentById(String id) {
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst();
    }
    @Override
    public Set<Student> getStudents() {
        return Collections.unmodifiableSet(students);
    }
    @Override
    public void addStudent(Student student) {
        students.add(student);
    }
}
