import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Student Management System (console application).
 * Demonstrates: Classes/Objects, Encapsulation, Inheritance,
 * Polymorphism and Abstraction in Java.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    // POLYMORPHISM: one list holds both Students and Lecturers (superclass reference)
    private static final List<Person> people = new ArrayList<>();
    private static final List<Course> courses = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:  addStudent();            break;
                case 2:  addLecturer();           break;
                case 3:  addCourse();             break;
                case 4:  assignLecturer();        break;
                case 5:  enrollStudent();         break;
                case 6:  recordMark();            break;
                case 7:  displayAllPeople();      break;
                case 8:  displayAllCourses();     break;
                case 9:  showReport();            break;
                case 10: loadSampleData();        break;
                case 0:
                    running = false;
                    System.out.println("Thank you. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // ===================== MENU =====================
    private static void printMenu() {
        System.out.println("\n=========================================");
        System.out.println("     STUDENT MANAGEMENT SYSTEM");
        System.out.println("=========================================");
        System.out.println(" 1. Add Student");
        System.out.println(" 2. Add Lecturer");
        System.out.println(" 3. Add Course");
        System.out.println(" 4. Assign Lecturer to Course");
        System.out.println(" 5. Enroll Student in Course");
        System.out.println(" 6. Record Student Mark");
        System.out.println(" 7. Display All People (Polymorphism)");
        System.out.println(" 8. Display All Courses");
        System.out.println(" 9. Show Report (Student / Lecturer)");
        System.out.println("10. Load Sample Data");
        System.out.println(" 0. Exit");
        System.out.println("-----------------------------------------");
    }

    // ===================== FEATURES =====================
    private static void addStudent() {
        System.out.println("\n--- Add Student ---");
        String id = readLine("Student ID   : ");
        if (findPerson(id) != null) {
            System.out.println("Error: ID already exists.");
            return;
        }
        String name = readLine("Name         : ");
        String email = readLine("Email        : ");
        String program = readLine("Program      : ");

        people.add(new Student(id, name, email, program));
        System.out.println("Student added successfully.");
    }

    private static void addLecturer() {
        System.out.println("\n--- Add Lecturer ---");
        String id = readLine("Lecturer ID  : ");
        if (findPerson(id) != null) {
            System.out.println("Error: ID already exists.");
            return;
        }
        String name = readLine("Name         : ");
        String email = readLine("Email        : ");
        String dept = readLine("Department   : ");

        people.add(new Lecturer(id, name, email, dept));
        System.out.println("Lecturer added successfully.");
    }

    private static void addCourse() {
        System.out.println("\n--- Add Course ---");
        String code = readLine("Course Code  : ").toUpperCase();
        if (findCourse(code) != null) {
            System.out.println("Error: Course code already exists.");
            return;
        }
        String title = readLine("Course Title : ");
        int credits = readInt("Credit Hours : ");

        courses.add(new Course(code, title, credits));
        System.out.println("Course added successfully.");
    }

    private static void assignLecturer() {
        System.out.println("\n--- Assign Lecturer to Course ---");
        Person p = findPerson(readLine("Lecturer ID  : "));
        Course c = findCourse(readLine("Course Code  : ").toUpperCase());

        if (!(p instanceof Lecturer)) {
            System.out.println("Error: Lecturer not found.");
        } else if (c == null) {
            System.out.println("Error: Course not found.");
        } else {
            ((Lecturer) p).assignCourse(c);   // downcast to use Lecturer-only method
            System.out.println(p.getName() + " is now teaching " + c.getTitle() + ".");
        }
    }

    private static void enrollStudent() {
        System.out.println("\n--- Enroll Student ---");
        Person p = findPerson(readLine("Student ID   : "));
        Course c = findCourse(readLine("Course Code  : ").toUpperCase());

        if (!(p instanceof Student)) {
            System.out.println("Error: Student not found.");
        } else if (c == null) {
            System.out.println("Error: Course not found.");
        } else if (((Student) p).enrollCourse(c)) {
            System.out.println(p.getName() + " enrolled in " + c.getTitle() + ".");
        } else {
            System.out.println("Student is already enrolled in this course.");
        }
    }

    private static void recordMark() {
        System.out.println("\n--- Record Mark ---");
        Person p = findPerson(readLine("Student ID   : "));
        Course c = findCourse(readLine("Course Code  : ").toUpperCase());

        if (!(p instanceof Student)) {
            System.out.println("Error: Student not found.");
            return;
        }
        if (c == null) {
            System.out.println("Error: Course not found.");
            return;
        }
        double mark = readDouble("Mark (0-100): ");
        if (((Student) p).recordMark(c, mark)) {
            System.out.printf("Mark saved. Grade: %s%n", Student.toGrade(mark));
        } else {
            System.out.println("Error: student not enrolled or mark out of range.");
        }
    }

    /**
     * RUNTIME POLYMORPHISM: the variable type is Person, but Java calls
     * the displayInfo() of the real object (Student or Lecturer).
     */
    private static void displayAllPeople() {
        System.out.println("\n--- All People ---");
        if (people.isEmpty()) {
            System.out.println("No records yet.");
            return;
        }
        int count = 1;
        for (Person person : people) {
            System.out.println("[" + count++ + "]");
            person.displayInfo();            // dynamic method dispatch
            System.out.println("-----------------------------------------");
        }
    }

    private static void displayAllCourses() {
        System.out.println("\n--- All Courses ---");
        if (courses.isEmpty()) {
            System.out.println("No courses yet.");
            return;
        }
        for (Course c : courses) {
            c.displayInfo();
        }
    }

    private static void showReport() {
        System.out.println("\n--- Show Report ---");
        Person p = findPerson(readLine("Enter Student/Lecturer ID: "));
        if (p instanceof Reportable) {
            Reportable r = (Reportable) p;   // interface reference
            System.out.println(r.generateReport());
        } else {
            System.out.println("Error: Person not found.");
        }
    }

    private static void loadSampleData() {
        if (findPerson("S001") != null) {
            System.out.println("Sample data already loaded.");
            return;
        }
        Lecturer l1 = new Lecturer("L001", "Dr. Aisyah Rahman", "aisyah@city.edu.my", "Information Technology");
        Student s1 = new Student("S001", "Ali Bin Abu", "ali@student.edu.my", "BIT");
        Student s2 = new Student("S002", "Siti Nur", "siti@student.edu.my", "BISE");
        Course c1 = new Course("BIT1123", "Object Oriented Programming", 4);
        Course c2 = new Course("BIT1013", "Database Fundamentals", 3);

        l1.assignCourse(c1);
        l1.assignCourse(c2);
        s1.enrollCourse(c1);
        s1.enrollCourse(c2);
        s2.enrollCourse(c1);
        s1.recordMark(c1, 85);
        s1.recordMark(c2, 72);
        s2.recordMark(c1, 64);

        people.add(l1);
        people.add(s1);
        people.add(s2);
        courses.add(c1);
        courses.add(c2);
        System.out.println("Sample data loaded (2 students, 1 lecturer, 2 courses).");
    }

    // ===================== HELPER METHODS =====================
    private static Person findPerson(String id) {
        for (Person p : people) {
            if (p.getId().equalsIgnoreCase(id.trim())) {
                return p;
            }
        }
        return null;
    }

    private static Course findCourse(String code) {
        for (Course c : courses) {
            if (c.getCode().equalsIgnoreCase(code.trim())) {
                return c;
            }
        }
        return null;
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /** Keeps asking until the user types a valid whole number. */
    private static int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    /** Keeps asking until the user types a valid number. */
    private static double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}