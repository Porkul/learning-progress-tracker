package tracker.model;

public enum CourseType {
    JAVA("Java", 600),
    DSA("DSA", 400),
    DATABASES("Databases", 480),
    SPRING("Spring", 550);
    private final String courseName;
    private final int requiredPoints;
    private int enrollmentCount;
    private int submissionCount;

    CourseType(String courseName, int requiredPoints) {
        this.courseName = courseName;
        this.requiredPoints = requiredPoints;
        this.enrollmentCount = 0;
        this.submissionCount = 0;

    }
    public String getCourseName() {
        return courseName;
    }
    public int getRequiredPoints() {
        return requiredPoints;
    }
    public int getEnrollmentCount() {
        return enrollmentCount;
    }

    public void incrementEnrollmentCount() {
        enrollmentCount++;
    }

    public void incrementSubmissionCount() {
        submissionCount++;
    }
}
