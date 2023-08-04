package tracker.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CourseStatistics {
    private final Course course;
    private Set<Student> accomplishedStudents;
    private List<Student> enrolledStudents;
    private int enrollmentCount;
    private int submissionCount;
    private double averageGrade;
    public CourseStatistics(Course course) {
        this.course = course;
        this.enrolledStudents = new ArrayList<>();
        this.accomplishedStudents = new HashSet<>();
    }
}

