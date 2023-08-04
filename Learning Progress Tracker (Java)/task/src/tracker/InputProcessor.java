package tracker;

import tracker.dto.ActivityStatisticsDto;
import tracker.dto.DifficultyStatisticsDto;
import tracker.dto.PopularityStatisticsDto;
import tracker.service.statistics.CourseStatisticsService;
import tracker.service.student.StudentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputProcessor {
    StudentService studentService;

    CourseStatisticsService statisticsService;

    public InputProcessor(StudentService studentService, CourseStatisticsService statisticsService) {
        this.studentService = studentService;
        this.statisticsService = statisticsService;
    }
    static final String ADD = "add students";
    static final String BACK = "back";
    static final String EXIT = "exit";
    static  final String ADD_POINTS = "add points";
    static  final String FIND = "find";
    static  final String LIST = "list";
    static  final String STATISTICS = "statistics";
    static  final String NOTIFY = "notify";

    public void processInput() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            boolean quit = false;
            while (!quit) {
                String action = reader.readLine().toLowerCase().trim();
                switch (action) {
                    case ADD -> addStudent(reader);
                    case ADD_POINTS -> addPoints(reader);
                    case FIND -> getStudentDetails(reader);
                    case LIST -> getAllStudents();
                    case STATISTICS -> getStatistics(reader);
                    case NOTIFY -> notifyStudent();
                    case BACK -> System.out.println("Enter 'exit' to exit the program.");
                    case EXIT -> {
                        quit = true;
                        System.out.println("Bye!");
                    }
                    case "" ->  System.out.println("No input.");
                    default -> System.out.println("Unknown command!");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void notifyStudent() {
        studentService.notifyStudent();
    }

    public void getStatistics(BufferedReader reader) throws IOException {
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        statisticsService.calculateStatistics();
        PopularityStatisticsDto popularityStatistics = statisticsService.getPopularityStatistics();
        printPopularityStatistics(popularityStatistics);
        ActivityStatisticsDto activityStatistics = statisticsService.getActivityStatistics();
        printActivityStatistics(activityStatistics);
        DifficultyStatisticsDto difficultyStatistics = statisticsService.getDifficultyStatistics();
        printDifficultyStatistics(difficultyStatistics);
        String input;
        while (true) {
            input = reader.readLine();
            if (input.equalsIgnoreCase("back")) {
                break;
            }
            statisticsService.printCourseStatistics(input.toUpperCase());
        }
    }

    private void printPopularityStatistics(PopularityStatisticsDto popularityStatistics) {
        System.out.println("Most popular: " + (popularityStatistics.getMostPopularCourses().isEmpty() ? "n/a" : String.join(", ", popularityStatistics.getMostPopularCourses())));
        System.out.println("Least popular: " + (popularityStatistics.getLeastPopularCourses().isEmpty() ? "n/a" : String.join(", ", popularityStatistics.getLeastPopularCourses())));
    }

    private void printDifficultyStatistics(DifficultyStatisticsDto difficultyStatistics) {
        if (difficultyStatistics.getEasiestCourse() == null && difficultyStatistics.getHardestCourse() == null) {
            System.out.println("Easiest course: n/a");
            System.out.println("Hardest course: n/a");
        } else if (difficultyStatistics.getEasiestCourse().equals(difficultyStatistics.getHardestCourse())) {
            System.out.println("Easiest course: n/a");
            System.out.println("Hardest course: n/a");
        } else {
            System.out.println("Easiest course: " + difficultyStatistics.getEasiestCourse());
            System.out.println("Hardest course: " + difficultyStatistics.getHardestCourse());
        }
    }

    private void printActivityStatistics(ActivityStatisticsDto dto) {
        System.out.println("Highest activity: " +
                (dto.getHighestSubmissionCount() == 0 || dto.getHighestActivityCourses().isEmpty() ?
                        "n/a" :
                        String.join(", ", dto.getHighestActivityCourses())));

        System.out.println("Lowest activity: " +
                (dto.getHighestSubmissionCount() == dto.getLowestSubmissionCount() || dto.getLowestActivityCourses().isEmpty() ?
                        "n/a" :
                        String.join(", ", dto.getLowestActivityCourses())));
    }


    public void getStudentDetails(BufferedReader reader) throws IOException {
        System.out.println("Enter an id or 'back' to return:");
        String input;
        while(true) {
            input = reader.readLine();
            if (input.equalsIgnoreCase("back")) {
                break;
            }
            // it is wrong the studentInput must not be an array
            String[] studentInput = input.split(" ");
            studentService.getStudentDetails(studentInput);
        }
    }
    public void addPoints(BufferedReader reader) throws IOException {
        System.out.println("Enter an id and points or 'back' to return:");
        String input;
        while (true) {
            input = reader.readLine();
            if (input.equalsIgnoreCase("back")) {
                break;
            }
            String[] studentInput = input.split(" ");
            try {
                if (studentInput.length == 5) {
                    studentService.addPoints(studentInput);
                } else {
                    throw new IllegalArgumentException("Incorrect points format.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private void addStudent(BufferedReader reader) throws IOException {
        System.out.println("Enter student credentials or 'back' to return");
        String input;
        while(true) {
            input = reader.readLine();
            if (input.equalsIgnoreCase("back")) {
                break;
            }
            String[] studentInput = input.split(" ");
            studentService.addStudent(studentInput);
        }
        System.out.printf("Total %d students have been added.\n",
                studentService.getStudents().size());
    }

    private void getAllStudents() {
        if (!studentService.getStudents().isEmpty()) {
            System.out.println("Students:");
            studentService.getStudents().forEach(System.out::println);
        } else {
            System.out.println("No students found.");
        }
    }

}