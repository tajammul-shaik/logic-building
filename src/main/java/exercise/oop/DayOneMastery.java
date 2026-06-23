package exercise.oop;

/**
 * DayOneMastery.java
 * * --- 1. THE JAVA EXECUTION ECOSYSTEM ---
 * To run this file, you technically use two tools from the JDK:
 * 1. Compile: `javac DayOneMastery.java` (Converts this human-readable code to bytecode)
 * 2. Execute: `java DayOneMastery` (The JVM loads the bytecode, and the JIT compiler executes it)
 */
public class DayOneMastery {

    // --- 3. VARIABLES: STATE AND SCOPE ---

    // STATIC VARIABLE:
    // Stored in the Metaspace. There is only ONE copy of this variable shared across
    // all objects of this class.
    static String globalGreeting = "Welcome to Day 1 Mastery!";

    // INSTANCE VARIABLE:
    // Stored on the Heap. If we created an object (`new DayOneMastery()`),
    // it would get its own copy. Automatically initialized to default (0 for int).
    int instanceCounter;

    public static void main(String[] args) {

        // LOCAL VARIABLE:
        // Stored on the Stack. It MUST be initialized before use, otherwise
        // the compiler will throw an error. It dies when main() finishes.
        int localCounter = 1;

        System.out.println("--- Execution Ecosystem ---");
        System.out.println(globalGreeting);

        // --- 2. PRIMITIVES & DATA TYPES ---
        System.out.println("\n--- Primitives ---");

        byte tinyNum = 127;          // 8-bit (Max value)
        short shortNum = 32000;      // 16-bit
        int standardNum = 100000;    // 32-bit (Default choice for integers)
        long massiveNum = 5000000L;  // 64-bit (Notice the 'L' suffix)

        float floatingNum = 10.5f;   // 32-bit (Notice the 'f' suffix)
        double preciseNum = 99.99;   // 64-bit (Default choice for decimals)

        char letter = 'A';           // 16-bit Unicode character
        boolean isMastering = true;  // JVM dependent (true or false only)

        System.out.println("Double value: " + preciseNum);
        System.out.println("Boolean value: " + isMastering);

        // --- 5. TYPE CASTING ---
        System.out.println("\n--- Type Casting ---");

        // IMPLICIT CASTING (Widening): Safe, automatic. Small box fits into a big box.
        long widenedNum = standardNum;
        System.out.println("Implicitly casted int to long: " + widenedNum);

        // EXPLICIT CASTING (Narrowing): Manual, risky. Forcing a big box into a small box.
        long largeValue = 130L;
        // We MUST use (byte) to tell the compiler we accept the data loss risk.
        byte narrowedByte = (byte) largeValue;

        // 130 is larger than a byte's max (127). The binary wraps around!
        System.out.println("Explicitly casted 130 to byte (Data overflow occurs!): " + narrowedByte);

        // --- 4. OPERATORS ---
        System.out.println("\n--- Operators ---");

        // SHORT-CIRCUIT LOGIC (&&)
        String text = null;
        // The '&&' prevents a NullPointerException. Because the left side (text != null)
        // is FALSE, Java completely skips the right side (text.length() > 0).
        if (text != null && text.length() > 0) {
            System.out.println("Text is valid.");
        } else {
            System.out.println("Short-circuit prevented a crash! 'text' is null.");
        }

        // BITWISE UNSIGNED RIGHT SHIFT (>>>)
        int negativeNum = -8;
        // A standard '>>' would preserve the negative sign.
        // '>>>' forcefully pushes 0s from the left, shifting the binary and turning it positive.
        int shifted = negativeNum >>> 1;

        System.out.println("Original Negative Binary: " + Integer.toBinaryString(negativeNum));
        System.out.println("After >>> 1 Shift:        " + Integer.toBinaryString(shifted));
        System.out.println("Resulting decimal: " + shifted);
    }
}
