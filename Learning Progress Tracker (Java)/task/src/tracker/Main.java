package tracker;

import tracker.dao.StudentDaoImpl;
import tracker.service.statistics.CourseStatisticsService;
import tracker.service.student.StudentService;
import tracker.validator.Validator;

public class Main {
    public static void main(String[] args) {
        System.out.println("Learning Progress Tracker");

        StudentDaoImpl studentDao = new StudentDaoImpl();
        Validator validator = new Validator();
        CourseStatisticsService statisticsService = new CourseStatisticsService();
        StudentService studentService = new StudentService(
                studentDao,validator, statisticsService);

        InputProcessor inputProcessor = new InputProcessor(studentService, statisticsService);
        inputProcessor.processInput();
    }
}
