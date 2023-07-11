package tracker.service;

import tracker.Main;
import tracker.model.Course;
import tracker.model.Student;
import tracker.validator.Validator;

import java.util.*;
import java.util.stream.Collectors;

public class StudentService {
    private Validator validator;
    private CourseService courseService;

    public StudentService(Validator validator, CourseService courseService) {
        this.validator = validator;
        this.courseService = courseService;
    }
    public void addStudent(Scanner scanner, Set<Student> students) {
        System.out.println("Enter student credentials or 'back' to return:");
        String input = scanner.nextLine().trim();
        while (!input.equals("back")) {
            Optional<Student> student = validator.validateCredentials(input, scanner, students);
            if(student.isPresent()){
                students.add(student.get());
                System.out.println("The student has been added.");
                input = scanner.nextLine();
            } else {
                break;
            }
        };
        System.out.printf("Total %d students have been added\n", students.size());
    }
    public void addPoints(Scanner scanner, Set<Student> students, String message) {
        System.out.println(message);
        String input = scanner.nextLine();
        String[] parts = input.trim().split("\\s+");
        if (input.equals("back")) {
            return;
        }

        if (parts.length != 5) {
            addPoints(scanner, students, "Incorrect points format");
            return;
        }

        if (!input.matches("^[0-9\\s]+$")) {
            String error = parts[0].matches("^[0-9\\s]+$")? "Incorrect points format" : "No student is found for id=" + parts[0];
            addPoints(scanner, students, error);
            return;
        };
        Optional<Student> student = findStudentById(parts[0], students);
        if (student.isEmpty()) {
            addPoints(scanner, students, "No student is found for id=" + parts[0]);
        } else {
            int[] points =
                    Arrays.stream(parts, 1, parts.length).mapToInt(Integer::parseInt).toArray();

            Collection<Course> courses = student.get().getCourses().values();

            courseService.addPointsToCourses(points, courses);

            addPoints(scanner, students, "Points updated");
        }
    }
    public Optional<Student> findStudentById(String input, Set<Student> students) {
        return input.matches("^\\d+$") ? students.stream()
                .filter(student -> student.getId().equals(Long.valueOf(input)))
                .findFirst() : Optional.empty();
    }


    public void getAllStudents(Set<Student> students) {
        if (!students.isEmpty()) {
            System.out.println("Students:");
            students.forEach(System.out::println);
        } else {
            System.out.println("No students found.");
        }
    }

    public void getStudentDetails(Scanner scanner, Set<Student> students, String message) {
        System.out.println(message);
        String input = scanner.nextLine().trim();
        if (input.equals("back")) {
            return;
        }
        Optional<Student> student = findStudentById(input, students);
        if (student.isPresent()) {
            StringBuilder sb = new StringBuilder(student.get().getId() + " points: " );
            String courseDetails = student.get().getCourses().stream()
                    .map(course -> course.getCourseType().getCourseName() + "=" + course.getPoints())
                    .collect(Collectors.joining(";"));
            sb.append(courseDetails);
            getStudentDetails(scanner, students, sb.toString());
        } else {
            getStudentDetails(scanner, students, "No student is found for id=" + input);
        }
    }
}


//    public void addStudent(Scanner scanner, Set<Student> students, String message) {
//        System.out.println(message);
//        String input = scanner.nextLine().trim();
//        if (input.equals("back")) {
//            System.out.printf("Total %d students have been added\n", students.size());
//            return;
//        }
//        Optional<Student> student = validator.validateCredentials(input, scanner, students);
//
//        if (student.isPresent()) {
//            students.add(student.get());
//            addStudent(scanner, students, "The student has been added.");
//        } else {
//            addStudent(scanner, students, "Total 0 students have been added");
//        }
////        while (!input.equals("back")) {
////            Optional<Student> student = validator.validateCredentials(input, scanner, students);
////            if(student.isPresent()){
////                students.add(student.get());
////                System.out.println("The student has been added.");
////                input = scanner.nextLine();
////            } else {
////                break;
////            }
////        };
//    }