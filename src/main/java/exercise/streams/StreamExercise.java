package exercise.streams;

import java.util.List;
import java.util.stream.Stream;

public class StreamExercise {
    public static void main(String[] args) {
//        List<String> names = List.of("Anna", "Bob", "Charlie", "David", "Ed");

        // filter + map + distinct + sorted
        // Sort the list
//        List<String> result = names.stream().map(String::toLowerCase).distinct().filter(n -> n.length() > 3).sorted().toList();
//        System.out.println(result);

        // Given a list of user names, filter out names that have less than 4 characters and convert the remaining names to uppercase.

//        List<String> result = names.stream().filter(name -> name.length() > 3).map(String::toUpperCase).toList();
//        System.out.println(result);



        // Given a list of product prices, find the highest price.
        List<Double> prices = List.of(19.99, 45.50, 89.00, 12.49);
        prices.stream().max(Double::compareTo).ifPresent(System.out::println);





    }
}
