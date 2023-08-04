package tracker.validator;

import tracker.model.Student;

import java.util.Set;

public class Validator {
    public boolean validateCredentials(String [] studentInput, Set<Student> students) {
        if (studentInput.length < 3) {
            System.out.println("Incorrect credentials.");
            return false;
        }
        String firstName = studentInput[0];
        String lastName = studentInput[studentInput.length - 2];
        String email = studentInput[studentInput.length - 1];

        return isValidLastName(lastName) && isValidName(firstName) && isValidEmail(email) && !isEmailTaken(email, students);
    }
//    public Optional<Student> validateCredentials(String [] studentInput, Set<Student> students) {
//        if (studentInput.length < 3) {
//            System.out.println("Incorrect credentials.");
//            return Optional.empty();
//        }
//        String firstName = studentInput[0];
//        String lastName = studentInput[studentInput.length - 2];
//        String email = studentInput[studentInput.length - 1];
//
//        if (!isValidLastName(lastName) || !isValidName(firstName) || !isValidEmail(email)) {
//            return Optional.empty();
//        }
//       if (isEmailTaken(email, students)) {
//           return Optional.empty();
//       }
//
//        return Optional.of(new Student(firstName, lastName, email));
//    }
    public boolean isValidName(String name) {
        String regex = "^[A-Za-z]+[-']?[A-Za-z]+";
        if (!name.matches(regex)) {
            System.out.println("Incorrect first name.");
            return false;
        }
        return true;
    }
    public boolean isValidLastName(String name) {
        String regex = "^[A-Za-z]{2,}(([\\s-'][A-Za-z]+)?)+|[A-Za-z]+[-'][A-Za-z]+";
        if (!name.matches(regex)) {
            System.out.println("Incorrect last name.");
            return false;
        }
        return true;
    }

    public boolean isValidEmail(String email) {
        String regex = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+";
        if (!email.matches(regex)) {
            System.out.println("Incorrect email.");
            return false;
        }
        return true;
    }

    public boolean isEmailTaken(String email, Set<Student> students) {
        for (Student student : students) {
            if (student.getEmail().equals(email)) {
                System.out.println("This email is already taken.");
                return true;
            }
        }
        return false;
    }

    public boolean isNonNegativeNumeric(String input) {
        if (!input.matches("\\b(?:0|[1-9]\\d*)\\b(?:\\s+\\b(?:0|[1-9]\\d*)\\b)*")) {
            System.out.println("Incorrect points format");
            return false;
        }
        return true;
    }

}