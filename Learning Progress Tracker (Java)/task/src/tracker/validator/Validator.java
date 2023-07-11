package tracker.validator;

import tracker.model.Student;

import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class Validator {
    public Optional<Student> validateCredentials(String input, Scanner scanner,
                                                 Set<Student> students) {
        String[] parts = input.split(" ");

        while (!input.equals("back")) {
            if (parts.length < 3) {
                System.out.println("Incorrect credentials.");
                input = scanner.nextLine();
                parts = input.split(" ");
                continue;
            }

            String firstName = parts[0];
            String lastName = parts[parts.length - 2];
            String email = parts[parts.length - 1];

            if (!isValidName(firstName)) {
                System.out.println("Incorrect first name.");
                input = scanner.nextLine();
                parts = input.split(" ");
                continue;
            }
            if (!isValidName(lastName)) {
                System.out.println("Incorrect last name.");
                input = scanner.nextLine();
                parts = input.split(" ");
                continue;
            }
            if (!isValidEmail(email)) {
                System.out.println("Incorrect email.");
                input = scanner.nextLine();
                parts = input.split(" ");
                continue;
            }
            if (isEmailTaken(email, students)) {
                System.out.println("This email is already taken.");
                input = scanner.nextLine();
                parts = input.split(" ");
                continue;
            }
            Long id = students.size() + 1L;
            return Optional.of(new Student(id, firstName, lastName, email));
        }

        return Optional.empty();
    }
    public boolean isValidName(String name) {
        String regex = "^(?![-'])(?=[-'A-Za-z]{2})(?=[A-Za-z])[A-Za-z]+(?:[-']?[A-Za-z]+)*(?<![-'\\s])$";
        return name.matches(regex);
    }

    public boolean isValidEmail(String email) {
        String regex = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+";

        return email.matches(regex);
    }

    public boolean isEmailTaken(String email, Set<Student> students) {
        for (Student student : students) {
            if (student.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean isNonNegativeNumeric(String input) {
        String regex = "\\b(?:0|[1-9]\\d*)\\b(?:\\s\\b(?:0|[1-9]\\d*)\\b)*";
        return input.matches(regex);
    }



}
