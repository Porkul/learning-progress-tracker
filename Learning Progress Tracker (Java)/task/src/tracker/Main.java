package tracker;

import tracker.service.CourseService;
import tracker.service.StudentService;
import tracker.validator.Validator;

public class Main {
    public static void main(String[] args) {
        System.out.println("Learning Progress Tracker");

        Validator validator = new Validator();
        CourseService courseService = new CourseService();
        StudentService studentService = new StudentService(validator, courseService);

        InputProcessor inputProcessor = new InputProcessor(studentService, courseService);
        inputProcessor.processInput();
    }
}
