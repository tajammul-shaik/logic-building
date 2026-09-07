import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelloWorld {
    public static void main(String[] args) {
        String s = "java java is is fun fun java java java is";

        List<String> listOfWords = Arrays.asList(s.split(" "));

        System.out.println("======= Given List =======");
        System.out.println(listOfWords);


        // Bruteforce approach using counter
        Map<String,Integer> map = new HashMap<>();
        for(int i = 0 ; i < listOfWords.size() ; i++) {
            int counter = 1;

            if(map.containsKey(listOfWords.get(i))) {
                counter = map.get(listOfWords.get(i))+1;
            }
            map.put(listOfWords.get(i), counter);

        }

        System.out.println("==================== Bruteforce approach using counter ===============");
        System.out.println(map);



        //word and it's count using forEach

        Map<String, Integer> wordCountUsingForEach = new HashMap<>();

        listOfWords.forEach(word -> {
            wordCountUsingForEach.put(word,wordCountUsingForEach.getOrDefault(word,0)+1);
        });

        System.out.println("========= Output using for each ==========");
        System.out.println(wordCountUsingForEach);


    }
}
