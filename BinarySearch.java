import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Creates a sorted list of random numbers and lets the user search it.
 */
public final class BinarySearch {

    /** Upper limit for generated numbers (exclusive). */
    private static final int RANDOM_BOUND = 101;

    /** Number of values in the list. */
    private static final int ARRAY_SIZE = 10;

    /**
     * Keeps this class from being instantiated.
     */
    private BinarySearch() {
    }

    /**
     * Searches a sorted array by repeatedly checking its midpoint.
     *
     * @param numbers the sorted values to search
     * @param target the value being searched for
     * @return the index of a matching value, or -1 if the value is absent
     */
    public static int binarySearch(final int[] numbers, final int target) {
        // The search starts with the entire array as the possible range.
        int low = 0;
        int high = numbers.length - 1;

        // Keep searching while at least one array position is still possible.
        while (low <= high) {
            // This form avoids overflowing when low and high are large.
            final int middle = low + (high - low) / 2;

            // The midpoint is the target, so return its position.
            if (numbers[middle] == target) {
                return middle;
            } else if (numbers[middle] < target) {
                // The target must be to the right of the midpoint.
                low = middle + 1;
            } else {
                // The target must be to the left of the midpoint.
                high = middle - 1;
            }
        }

        // The possible range became empty, so the target is not in the array.
        return -1;
    }

    /**
     * Makes an array of random integers between 0 and 100.
     *
     * @return an array filled with random integers
     */
    public static int[] populateArray() {
        // Allocate space for the values and create a random-number generator.
        final int[] numbers = new int[ARRAY_SIZE];
        final Random random = new Random();

        // Fill each position with a random value from 0 through 100.
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(RANDOM_BOUND);
        }

        return numbers;
    }

    /**
     * Runs the number-searching game until the user quits.
     *
     * @param args command-line arguments
     */
    public static void main(final String[] args) {
        // Automatically close the scanner when the game ends.
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(
                "Choose a number from the list to search for. "
                    + "Type 'q' to quit."
            );

            // Start with non-quit input so the game runs at least once.
            String input = "";
            while (!"q".equalsIgnoreCase(input)) {
                final int[] numbers = populateArray();
                // Binary search requires the values to be sorted.
                Arrays.sort(numbers);

                // Show the sorted values so the user can choose a number.
                System.out.println(Arrays.toString(numbers));
                System.out.print("Number: ");
                System.out.flush();

                // Stop cleanly if the input stream has no more lines.
                if (!scanner.hasNextLine()) {
                    break;
                }
                input = scanner.nextLine().trim();

                // Quit before trying to interpret the command as a number.
                if ("q".equalsIgnoreCase(input)) {
                    System.out.println("Thanks for playing!");
                    continue;
                }

                try {
                    // Convert the user's input and search the sorted array.
                    final int target = Integer.parseInt(input);
                    final int index = binarySearch(numbers, target);

                    // A non-negative index means the target was found.
                    if (index >= 0) {
                        System.out.println(
                            target + " is in the list at index "
                                + index + "."
                        );
                    } else {
                        System.out.println(target + " is not in the list.");
                    }
                } catch (final NumberFormatException exception) {
                    // Explain the accepted input instead of ending the game.
                    System.out.println(
                        "Invalid input. Enter a whole number or 'q' to quit."
                    );
                }
            }
        }
    }
}
