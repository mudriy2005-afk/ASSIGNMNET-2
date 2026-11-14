import java.util.ArrayList;
import java.util.List;

/**
 * Lecturer is another subclass of Person (INHERITANCE).
 * Its behaviour differs from Student, which shows POLYMORPHISM.
 */
public class Lecturer extends Person implements Reportable {

    private String department;
    private List<Course> courses = new ArrayList<>();

    public Lecturer(String id, String name, String email, String department) {
        super(id, name, email);
        this.department = department;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    /** Assign a course to this lecturer (and tell the course who teaches it). */
    public void assignCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
            course.setLecturer(this);
        }
    }

    @Override
    public String getRole() {
        return "Lecturer";
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.printf("%-10s : %s%n", "Department", department);
        System.out.printf("%-10s : %d%n", "Teaching", courses.size());
    }

    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("========== TEACHING REPORT ==========\n");
        sb.append(String.format("Lecturer   : %s (%s)%n", getName(), getId()));
        sb.append(String.format("Department : %s%n", department));
        sb.append("-------------------------------------\n");
        if (courses.isEmpty()) {
            sb.append("No courses assigned.\n");
        }
        for (Course c : courses) {
            sb.append(String.format("%-8s %s%n", c.getCode(), c.getTitle()));
        }
        sb.append("=====================================");
        return sb.toString();
    }
}