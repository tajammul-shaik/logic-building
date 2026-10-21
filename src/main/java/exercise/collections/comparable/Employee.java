package exercise.collections.comparable;

import java.util.Objects;

/**
 * Domain object with a NATURAL ORDERING baked in via Comparable.
 *
 * WHEN TO USE Comparable:
 * - There is one obvious, "default" way to order this type wherever it's used.
 * - You own the class and can add compareTo to it.
 *
 * WHEN NOT TO USE Comparable (use a Comparator instead - see the sibling `comparator` package):
 * - You need more than one ordering (sometimes by name, sometimes by salary, ...).
 * - You don't own the class (e.g. a library/framework type) and can't add compareTo to it.
 */
public class Employee implements Comparable<Employee> {
    private final String name;
    private final int salary;

    public Employee(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    /**
     * BEST PRACTICE: use Integer.compare()/Double.compare() instead of manual subtraction.
     * AVOID: `return this.salary - other.salary;` - for large ints this can overflow and
     * silently wrap to a negative number, flipping the ordering without ever throwing.
     */
    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.salary, other.salary);
    }

    /**
     * CONTRACT NOTE (best practice, deliberately broken here to demonstrate the risk):
     * compareTo should ideally be "consistent with equals": (x.compareTo(y) == 0) should imply
     * x.equals(y). This class breaks that on purpose - two employees with the same salary but
     * different names compare as "equal" (compareTo == 0) even though equals() says they differ.
     * See ComparableDemo for the concrete collection bug this causes (a TreeSet silently
     * dropping an element).
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return salary == employee.salary && Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, salary);
    }

    @Override
    public String toString() {
        return name + "($" + salary + ")";
    }
}