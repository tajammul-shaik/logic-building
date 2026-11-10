package exercise.Prep;

public class ReverseStringOrNumber {


    static int bruteForceIntReverse(int x){

        String s = new StringBuilder(String.valueOf(Math.abs((long) x))).reverse().toString();
        System.out.println("num is converted to string : "+ s);

        long parseFromStringTolong = Long.parseLong(s);

        System.out.println(parseFromStringTolong);

        if (x < 0)
            parseFromStringTolong = - parseFromStringTolong;

        return parseFromStringTolong > Integer.MAX_VALUE || parseFromStringTolong < Integer.MIN_VALUE ? 0 : (int) parseFromStringTolong;

    }

    public static void main(String[] args) {
        int num = 1234;
        System.out.println("Num before bruteforce reverse : " +num);
        bruteForceIntReverse(num);
    }
}
