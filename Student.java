import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Student is a subclass of Person (INHERITANCE: extends).
 * It also implements the Reportable interface (ABSTRACTION).
 */
public class Student extends Person implements Reportable {

    private String program;
    // Course -> mark (0-100). LinkedHashMap keeps insertion order.
    private Map<Course, Double> marks = new LinkedHashMap<>();

    public Student(String id, String name, String email, String program) {
        super(id, name, email);          // call superclass constructor
        this.program = program;
    }

    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }

    public int getTotalCourses() { return marks.size(); }

    /** Enrol into a course. Returns false if already enrolled. */
    public boolean enrollCourse(Course course) {
        if (marks.containsKey(course)) {
            return false;
        }
        marks.put(course, -1.0);         // -1 means "no mark yet"
        return true;
    }

    public boolean isEnrolled(Course course) {
        return marks.containsKey(course);
    }

    /** Record a mark (0-100) for an enrolled course. */
    public boolean recordMark(Course course, double mark) {
        if (!marks.containsKey(course) || mark < 0 || mark > 100) {
            return false;
        }
        marks.put(course, mark);
        return true;
    }

    /** Convert a mark into a letter grade using if-else control structure. */
    public static String toGrade(double mark) {
        if (mark >= 80) return "A";
        else if (mark >= 70) return "B";
        else if (mark >= 60) return "C";
        else if (mark >= 50) return "D";
        else return "F";
    }

    /** Convert a letter grade into grade points using a switch statement. */
    public static double toGradePoint(String grade) {
        switch (grade) {
            case "A": return 4.0;
            case "B": return 3.0;
            case "C": return 2.0;
            case "D": return 1.0;
            default:  return 0.0;
        }
    }

    /** GPA = sum(gradePoint x creditHours) / sum(creditHours) for marked courses. */
    public double calculateGPA() {
        double totalPoints = 0;
        int totalCredits = 0;
        for (Map.Entry<Course, Double> entry : marks.entrySet()) {
            double mark = entry.getValue();
            if (mark >= 0) {
                int credits = entry.getKey().getCreditHours();
                totalPoints += toGradePoint(toGrade(mark)) * credits;
                totalCredits += credits;
            }
        }
        return (totalCredits == 0) ? 0.0 : totalPoints / totalCredits;
    }

    // ---------- Method OVERRIDING (POLYMORPHISM) ----------
    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public void displayInfo() {
        super.displayInfo();             // reuse the superclass version
        System.out.printf("%-10s : %s%n", "Program", program);
        System.out.printf("%-10s : %d%n", "Courses", marks.size());
    }

    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("========== ACADEMIC REPORT ==========\n");
        sb.append(String.format("Student : %s (%s)%n", getName(), getId()));
        sb.append(String.format("Program : %s%n", program));
        sb.append("-------------------------------------\n");
        if (marks.isEmpty()) {
            sb.append("Not enrolled in any course.\n");
        }
        for (Map.Entry<Course, Double> entry : marks.entrySet()) {
            Course c = entry.getKey();
            double m = entry.getValue();
            String markText = (m < 0) ? "No mark yet" :
                    String.format("%.1f (%s)", m, toGrade(m));
            sb.append(String.format("%-8s %-28s %s%n", c.getCode(), c.getTitle(), markText));
        }
        sb.append("-------------------------------------\n");
        sb.append(String.format("GPA     : %.2f%n", calculateGPA()));
        sb.append("=====================================");
        return sb.toString();
    }
}