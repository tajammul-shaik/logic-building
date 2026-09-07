package exercise.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamsExamples {

    /*
    * --------------- Learning Streams -----------------
    * Phase 1: Understanding Stream Mechanics
    *   [ Data Source ]  --->  [ Intermediate Ops ]  --->  [ Terminal Op ]
    *    (Collection)           (Lazy: filter, map)          (Eager: collect, reduce)
    *
    *
    * Phase 2: Essential Operations & Better Approaches
    * Phase 3: Advanced Collectors (Collectors.groupingBy)
    * Phase 4: Best Practices & Common Anti-Patterns
    *
    *
    * */


    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Anna");

        List<String> result = names.stream()
                .filter(name -> name.startsWith("A"))     // Predicate: keep names starting with 'A'
                .map(String::toUpperCase)                // Function: convert to uppercase
                .sorted()                                // Intermediate: sort alphabetically
                .collect(Collectors.toList());           // Terminal: collect to a List

        // Output: ["ALICE", "ANNA"]

        System.out.println(result);
    }
}
