package tracker.model;

import java.util.HashMap;
import java.util.Map;

public class Course {
    private final CourseType courseType;
    private boolean isCompleted;

    public CourseType getCourseType() {
        return courseType;
    }

    private int points;
    private Map<Student, Integer> students = new HashMap<>();
    public Course(CourseType courseType) {
        this.courseType = courseType;
        this.isCompleted = false;
        this.points = 0;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
//        if (this.points > 0) {
//            enrollmentCount++;
//        } else if (this.points >= requiredPoints)) {
//            this.isCompleted = true;
//        }
    }

}