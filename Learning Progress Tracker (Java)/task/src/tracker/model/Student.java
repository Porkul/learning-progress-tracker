package tracker.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class Student {
     private final String id;
     private final String firstName;
     private final String lastName;
     private final String email;
     private Map<Course, Integer> coursePoints;

     private static int nextStudentId = 1000;

     public Student(String firstName, String lastName, String email) {
          this.id = String.valueOf(nextStudentId++);
          this.firstName = firstName;
          this.lastName = lastName;
          this.email = email;
          this.coursePoints = new HashMap<>();
          for (Course course : Course.values()) {
               coursePoints.put(course, 0);
          }
     }

     public String getEnrollmentsPoints() {
          return id + " points: " + Arrays.stream(Course.values())
                  .map(course -> {
                       int points = coursePoints.get(course);
                       return course.getName() + "=" + points;
                  })
                  .collect(Collectors.joining(";"));
     }
     public int getPointsForCourse(Course course) {
          return coursePoints.get(course);
     }
     @Override
     public String toString() {
          StringBuilder sb = new StringBuilder();
          sb.append(id);

          for (Integer points : coursePoints.values()) {
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
