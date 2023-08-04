package tracker.dao;

import tracker.model.Student;

import java.util.Optional;
import java.util.Set;

public interface StudentDao {
    public Optional<Student> getStudentById(String id);
    public Set<Student> getStudents();
    public void addStudent(Student student);
}
