package exercise.practice;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class FindFirstRepeatingCharacter {

    /**
     * Finds the first repeating character in a string.
     * Compatible with Optional<Character> handling.
     */
    public static Optional<Character> repeatingChar(String word) {
        if (word == null || word.length() < 2) {
            return Optional.empty();
        }

        Set<Character> seen = new HashSet<>();
        for (char c : word.toCharArray()) {
            if (!seen.add(c)) {
                return Optional.of(c);
            }
        }

        return Optional.empty();
    }

    public static void main(String[] args) {
        // Array of test strings
        String[] examples = {
                "Programming",
                "geeksforgeeks",
                "abcdef",
                "",
                null
        };

        // Iterating and testing each string in the array
        for (String word : examples) {
            Optional<Character> result = repeatingChar(word);

            String displayWord = (word == null) ? "null" : "\"" + word + "\"";
            String output = result.map(c -> "'" + c + "'").orElse("No duplicate");

            System.out.printf("Input: %-15s -> First Repeating: %s%n", displayWord, output);
        }
    }
}
