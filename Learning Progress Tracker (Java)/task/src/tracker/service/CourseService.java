package tracker.service;

import tracker.model.Course;
import tracker.model.CourseType;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CourseService {
    public void addPointsToCourses (int[] points,  Map<String, Course> courses ) {

        for (int i = 0; i < points.length; i++) {
            int currentPoints = points[i];
            Course currentCourse = courses.get()

            currentCourse.setPoints(currentPoints + currentCourse.getPoints());

            if (currentPoints > 0) {
                CourseType courseType = currentCourse.getCourseType();
                courseType.incrementSubmissionCount();
                if (currentCourse.getPoints() == 0) {
                    courseType.incrementEnrollmentCount();
                }
            }
        }
    }
    public void printStatistics(Scanner scanner) {
        System.out.println("Type the name of a course to see details or 'back' to quit");
        System.out.println("Most popular: " + getMostPopularCourse());
        System.out.println("Least popular: " + "n/a");
        System.out.println("Highest activity: " + "n/a");
        System.out.println("Lowest activity: " + "n/a");
        System.out.println("Easiest course: " + "n/a");
        System.out.println("Hardest course: " + "n/a");

        String input = scanner.nextLine().trim();
        while (!input.equals("back")) {
            for (CourseType courseType : CourseType.values()) {
                if (courseType.getCourseName().equalsIgnoreCase(input)) {
                    getCourseDetails(courseType);
                    System.out.println(courseType);
                } else {
                    System.out.println("Unknown course.");
                }
            }

            // how check here if input equals "java", "dsa", "databases" or "spring
        }
    }

    public String getMostPopularCourse () {
        CourseType mostPopularCourse = null;
        for (CourseType courseType : CourseType.values()) {
            if (mostPopularCourse == null || courseType.getEnrollmentCount() > 0 && courseType.getEnrollmentCount() > mostPopularCourse.getEnrollmentCount()) {
                mostPopularCourse = courseType;
            } else {
                return "n/a";
            }
        }
        return mostPopularCourse.getCourseName();
    }

    public void getCourseDetails(CourseType courseType) {

    }
}
