package tracker;

import tracker.model.Student;
import tracker.service.CourseService;
import tracker.service.StudentService;

import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class InputProcessor {
    StudentService studentService;
    CourseService courseService;

    public InputProcessor(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }
    Set<Student> students = new LinkedHashSet<>();
    static final String ADD = "add students";
    static final String BACK = "back";
    static final String EXIT = "exit";
    static  final String ADD_POINTS = "add points";
    static  final String FIND = "find";
    static  final String LIST = "list";
    static  final String STATISTICS = "statistics";


    public void processInput() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            String action = scanner.nextLine().toLowerCase().trim();
            switch (action) {
                case ADD -> this.studentService.addStudent(scanner, students);
                case ADD_POINTS -> this.studentService.addPoints(scanner, students, "Enter an id and points or 'back' to return:");
                case FIND -> this.studentService.getStudentDetails(scanner, students, "Enter an id or 'back' to return:");
                case LIST -> this.studentService.getAllStudents(students);
                case STATISTICS -> {this.courseService.printStatistics(scanner);}
                case BACK -> System.out.println("Enter 'exit' to exit the program.");
                case EXIT -> {
                    quit = true;
                    System.out.println("Bye!");
                }
                case "" ->  System.out.println("No input.");
                default -> System.out.println("Unknown command!");
            }
        };
    }
}
