// [[1,"hi"],["boy",2,3,],[4,"home"]]
// 1,boy,3,home
// constrain is can't merge the sub array

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        List<List<Object>> items = Arrays.asList(
                Arrays.asList(1,"hi"), Arrays.asList("boy",2,3), Arrays.asList(4,"home")
        );
        List<Object> output = new ArrayList<>();

        int index = 0;
        for (List<Object> sublist : items){
            for(Object val : sublist){
                if(index % 2 ==0)
                    output.add(val);

                index++;
            }
        }
        System.out.println(output);
    }
}
