package tracker.model;

import java.util.*;

public class Student {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public void setId(Long id) {
        this.id = id;
    }

//    private final List<Course> courses = new ArrayList<>();
    private final Map<String, Course> courses = new HashMap<>();

    public Student(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        for (CourseType courseType : CourseType.values()) {
            courses.put(courseType.getCourseName(), new Course(courseType));
        }
    }
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Map<String, Course> getCourses() {
        return courses;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id);

        for (Course course : courses.values()) {
            int points = course.getPoints();
            sb.append(" ").append(points != 0 ? points : "");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(email, student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

}
