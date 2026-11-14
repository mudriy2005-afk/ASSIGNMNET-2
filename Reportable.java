/**
 * Interface (ABSTRACTION): any class that can produce a text report.
 * Classes that implement this interface MUST implement generateReport().
 */
public interface Reportable {
    String generateReport();
}