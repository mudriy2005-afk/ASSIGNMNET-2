/**
 * Represents a course offered by the university.
 * A Course is taught by one Lecturer (association).
 */
public class Course {

    private String code;
    private String title;
    private int creditHours;
    private Lecturer lecturer;   // may be null until assigned

    public Course(String code, String title, int creditHours) {
        this.code = code;
        this.title = title;
        setCreditHours(creditHours);
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCreditHours() { return creditHours; }

    /** Validation inside the setter is a key benefit of encapsulation. */
    public void setCreditHours(int creditHours) {
        this.creditHours = (creditHours > 0) ? creditHours : 3;
    }

    public Lecturer getLecturer() { return lecturer; }
    public void setLecturer(Lecturer lecturer) { this.lecturer = lecturer; }

    public void displayInfo() {
        String lecturerName = (lecturer == null) ? "Not assigned" : lecturer.getName();
        System.out.printf("%-10s %-32s %2d credit hours | Lecturer: %s%n",
                code, title, creditHours, lecturerName);
    }
}