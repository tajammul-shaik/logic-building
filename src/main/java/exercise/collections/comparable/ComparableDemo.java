package exercise.collections.comparable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * ComparableDemo.java
 *
 * Shows natural ordering (Comparable) in action, then a concrete example of what goes wrong
 * when compareTo isn't consistent with equals.
 */
public class ComparableDemo {

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>(List.of(
                new Employee("Charlie", 50_000),
                new Employee("Alice", 70_000),
                new Employee("Bob", 50_000) // same salary as Charlie, different name/identity
        ));

        System.out.println("--- 1. Natural ordering via Collections.sort() ---");
        Collections.sort(employees); // delegates to Employee.compareTo -> ascending by salary
        System.out.println(employees);

        System.out.println("\n--- 2. List.sort(null) also uses natural ordering ---");
        employees.sort(null);
        System.out.println(employees);

        System.out.println("\n--- 3. AVOID: compareTo inconsistent with equals + TreeSet ---");
        System.out.println("Expected 3 distinct employees. TreeSet uses ONLY compareTo to decide");
        System.out.println("uniqueness, and Bob/Charlie compare as equal (same salary), so one is");
        System.out.println("silently dropped even though equals() says they are different people.");
        Set<Employee> treeSet = new TreeSet<>(employees);
        System.out.println("TreeSet size: " + treeSet.size() + " -> " + treeSet);

        System.out.println("\n--- 4. Contrast: HashSet uses equals/hashCode, keeps all 3 ---");
        Set<Employee> hashSet = new HashSet<>(employees);
        System.out.println("HashSet size: " + hashSet.size() + " -> " + hashSet);
    }
}