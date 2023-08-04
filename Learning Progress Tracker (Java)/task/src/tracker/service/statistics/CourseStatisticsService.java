package tracker.service.statistics;

import tracker.dto.ActivityStatisticsDto;
import tracker.dto.DifficultyStatisticsDto;
import tracker.dto.PopularityStatisticsDto;
import tracker.model.Course;
import tracker.model.CourseStatistics;
import tracker.model.Student;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class CourseStatisticsService {
    //need to be static
    private final Set<CourseStatistics> courseStatisticsList;
    public CourseStatisticsService() {
        courseStatisticsList = new HashSet<>();
        for (Course course : Course.values()) {
            courseStatisticsList.add(new CourseStatistics(course));
        }
    }
    public CourseStatistics getCourseStatisticsByCourse(Course course) {
        return courseStatisticsList.stream()
                .filter(cs -> cs.getCourse() == course)
                .findFirst()
                .orElse(null);
    }
    public Set<CourseStatistics> getCourseStatisticsList() {
        return Collections.unmodifiableSet(courseStatisticsList);
    }
    private List<String> getCoursesWithSubmissionCount(int submissionCount) {
        return courseStatisticsList.stream()
                .filter(courseStatistics -> courseStatistics.getSubmissionCount() == submissionCount)
                .map(courseStatistics -> courseStatistics.getCourse().getName())
                .toList();
    }
    private String getCoursesWithAverageGrade(double averageGrade) {
        return courseStatisticsList.stream()
                .filter(courseStatistics -> courseStatistics.getAverageGrade() == averageGrade)
                .findFirst()
                .get()
                .getCourse()
                .getName();
    }
    private List<String> getMostPopularCourses(int maxEnrollmentCount) {
        return courseStatisticsList.stream()
                .filter(courseStatistics -> courseStatistics.getEnrollmentCount() == maxEnrollmentCount)
                .map(courseStatistics -> courseStatistics.getCourse().getName())
                .toList();
    }
    private List<String> getLeastPopularCourses(int minEnrollmentCount) {
        return courseStatisticsList.stream()
                .filter(courseStatistics -> courseStatistics.getEnrollmentCount() == minEnrollmentCount)
                .map(courseStatistics -> courseStatistics.getCourse().getName())
                .toList();
    }
    public PopularityStatisticsDto getPopularityStatistics() {
        PopularityStatisticsDto dto = new PopularityStatisticsDto();

        IntSummaryStatistics stats = courseStatisticsList.stream()
                .mapToInt(CourseStatistics::getEnrollmentCount)
                .summaryStatistics();

        if (stats.getCount() > 0) {
            int maxEnrollmentCount = stats.getMax();
            int minEnrollmentCount = stats.getMin();

            if (maxEnrollmentCount > 0) {
                dto.setMostPopularCourses(getMostPopularCourses(maxEnrollmentCount));
            }
            if (maxEnrollmentCount != minEnrollmentCount) {
                dto.setLeastPopularCourses(getLeastPopularCourses(minEnrollmentCount));
            }
        }
        return dto;
    }
    public ActivityStatisticsDto getActivityStatistics() {
        ActivityStatisticsDto dto = new ActivityStatisticsDto();

        IntSummaryStatistics submissionCountStats = courseStatisticsList.stream()
                .mapToInt(CourseStatistics::getSubmissionCount)
                .summaryStatistics();

        if (submissionCountStats.getCount() > 0) {
            int highestSubmissionCount = submissionCountStats.getMax();
            List<String> highestActivityCourses = getCoursesWithSubmissionCount(highestSubmissionCount);
            dto.setHighestActivityCourses(highestActivityCourses);
            dto.setHighestSubmissionCount(highestSubmissionCount);

            int lowestSubmissionCount = submissionCountStats.getMin();
            List<String> lowestActivityCourses = getCoursesWithSubmissionCount(lowestSubmissionCount);
            dto.setLowestActivityCourses(lowestActivityCourses);
            dto.setLowestSubmissionCount(lowestSubmissionCount);
        }
        return dto;
    }
    public DifficultyStatisticsDto getDifficultyStatistics() {
        DifficultyStatisticsDto dto = new DifficultyStatisticsDto();

        DoubleSummaryStatistics gradeStats = courseStatisticsList.stream()
                .mapToDouble(CourseStatistics::getAverageGrade)
                .filter(grade -> grade != 0.0)
                .summaryStatistics();

        if (gradeStats.getCount() > 0) {
            double highestAverageGrade = gradeStats.getMax();
            dto.setEasiestCourse(getCoursesWithAverageGrade(highestAverageGrade));

            double lowestAverageGrade = gradeStats.getMin();
            dto.setHardestCourse(getCoursesWithAverageGrade(lowestAverageGrade));
        }

        return dto;
    }
    public void printCourseStatistics(String courseInput) {
        try {
            Course requiredCourse = Course.valueOf(courseInput);
            CourseStatistics courseStatistics = getCourseStatisticsByCourse(requiredCourse);
            System.out.println(requiredCourse.getName());
            System.out.printf("%-7s %-7s %-10s%n", "id", "points", "completed");

            DecimalFormat decimalFormat = new DecimalFormat("0.0%");
            decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

            List<Student> students = courseStatistics.getEnrolledStudents()
                    .stream()
                    .sorted(Comparator.comparingInt((Student student) -> student.getPointsForCourse(requiredCourse))
                            .reversed()
                            .thenComparing(Student::getId)).toList();

            students.forEach(student -> {
                String id = student.getId();
                int points = student.getPointsForCourse(requiredCourse);
                double completed = (double) points / requiredCourse.getRequiredPoints();
                String completionPercentage = decimalFormat.format(completed);
                System.out.printf("%-7s %-7d %s%n", id, points, completionPercentage);
            });
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown course");
        }
    }
    public void calculateStatistics() {
        for (CourseStatistics courseStatistics : courseStatisticsList) {
            List<Student> enrolledStudents = courseStatistics.getEnrolledStudents();
            int enrollmentCount = enrolledStudents.size();
            int submissionCount = 0;
            double averageGrade = 0.0;

            if (enrollmentCount > 0) {
                for (Student student : enrolledStudents) {
                    int points = student.getPointsForCourse(courseStatistics.getCourse());
                    submissionCount += (points > 0) ? 1 : 0;
                    averageGrade += points;
                }
                averageGrade /= enrollmentCount;
            }
            courseStatistics.setEnrollmentCount(enrollmentCount);
            courseStatistics.setSubmissionCount(submissionCount);
            courseStatistics.setAverageGrade(averageGrade);
        }
    }
}
