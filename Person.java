/**
 * Abstract superclass (INHERITANCE + ABSTRACTION).
 * Holds data shared by every person in the system.
 * It cannot be instantiated directly: use Student or Lecturer.
 */
public abstract class Person {

    // ENCAPSULATION: attributes are private
    private String id;
    private String name;
    private String email;

    public Person(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // ---------- Getters and Setters ----------
    public String getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    /**
     * Abstract method (ABSTRACTION): every subclass decides its own role.
     */
    public abstract String getRole();

    /**
     * Displays basic information. Subclasses OVERRIDE this method
     * to add their own details (POLYMORPHISM).
     */
    public void displayInfo() {
        System.out.printf("%-10s : %s%n", "Role", getRole());
        System.out.printf("%-10s : %s%n", "ID", id);
        System.out.printf("%-10s : %s%n", "Name", name);
        System.out.printf("%-10s : %s%n", "Email", email);
    }
}