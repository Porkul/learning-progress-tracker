package tracker.service.student;

import tracker.dao.StudentDao;
import tracker.model.Course;
import tracker.model.CourseStatistics;
import tracker.model.Student;
import tracker.service.statistics.CourseStatisticsService;
import tracker.validator.Validator;

import java.util.*;

public class StudentService {
    private final Map<Student, Set<Course>> notifiedStudents = new HashMap<>();
    CourseStatisticsService statisticsService;
    StudentDao studentDao;
    Validator validator;
    public StudentService(StudentDao studentDao, Validator validator, CourseStatisticsService statisticsService) {
        this.statisticsService = statisticsService;
        this.studentDao = studentDao;
        this.validator = validator;
    }
    public Set<Student> getStudents() {
        return studentDao.getStudents();
    }
    public void getStudentDetails(String[] studentInput) {
        String id = studentInput[0];
        Optional<Student> foundStudent = validator.isNonNegativeNumeric(id)?
                studentDao.getStudentById(id) : Optional.empty();
        foundStudent.ifPresent(student -> System.out.println(student.getEnrollmentsPoints()));
    }
    public void addStudent(String[] studentInput) {
        if (!validator.validateCredentials(studentInput, getStudents())) {
            return;
        }
        String firstName = studentInput[0];
        String lastName =  studentInput[studentInput.length - 2];
        String email =  studentInput[studentInput.length - 1];
        Student student = new Student(firstName, lastName, email);
        studentDao.addStudent(student);
        System.out.println("The student has been added.");
    }
    public void addPoints(String[] studentInput) {
        String id = studentInput[0];
        Optional<Student> foundStudent = validator.isNonNegativeNumeric(id)?
                studentDao.getStudentById(id) : Optional.empty();
        if (foundStudent.isPresent()) {
            Course[] courses = {Course.JAVA, Course.DSA, Course.DATABASES, Course.SPRING};

            for (int i = 1; i < studentInput.length; i++) {
                String pointsInput = studentInput[i];

                if (!validator.isNonNegativeNumeric(pointsInput)) {
                    return; // Invalid points value
                }

                int points = Integer.parseInt(pointsInput);
                Course currentCourse = courses[i - 1];
                Student student = foundStudent.get();

                if (points > 0) {
                    // enroll student
                    CourseStatistics courseStatistics =
                            statisticsService.getCourseStatisticsByCourse(currentCourse);

                    student
                            .getCoursePoints()
                            .compute(currentCourse,(course, existingPoints) -> {
                                if (existingPoints != null) {
                                    int totalPoints = existingPoints + points;
                                    if (totalPoints >= currentCourse.getRequiredPoints()) {
                                        courseStatistics.getAccomplishedStudents().add(student);
                                    }
                                    return totalPoints;
                                }
                                return points;
                            });


                    if (courseStatistics != null) {
                        List<Student> enrolledStudents = courseStatistics.getEnrolledStudents();
                        if (!enrolledStudents.contains(student)) {
                            enrolledStudents.add(student);
                        }
                    }
                }
            }
            System.out.println("Points updated.");
        } else {
          System.out.printf("No student is found for id=%s%n", id);
        }
    }
    public void notifyStudent() {
        Set<CourseStatistics> courseStatisticsList = statisticsService.getCourseStatisticsList();
        for (CourseStatistics stat : courseStatisticsList) {
            Set<Student> accomplishedStudents = stat.getAccomplishedStudents();
            Course currentCourse = stat.getCourse();

            for (Student student : accomplishedStudents) {
                // Retrieve the existing courses for the student, or create a new set if there are none
                Set<Course> studentCourses = notifiedStudents.getOrDefault(student, new HashSet<>());

                if (studentCourses.contains(currentCourse)) {
                    System.out.println("Total 0 students notified");
                    return; // Skip this iteration and move to next student
                }

                System.out.println("To: " + student.getEmail());
                System.out.println("Re: Your Learning Progress");
                System.out.println("Hello, " + student.getFirstName() + " " + student.getLastName() + ", You have accomplished our " + currentCourse.getName() + " course!");

                // Add the course to the student's set of courses
                studentCourses.add(currentCourse);

                // Update the student's entry in the map
                notifiedStudents.put(student, studentCourses);
            }
        }

        System.out.println("Total " + notifiedStudents.size() + " students have been notified.");
    }
}
