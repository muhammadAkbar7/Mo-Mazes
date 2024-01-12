package problems;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - Do not add any additional imports
 * - Do not create new `int[]` objects for `toString` or `rotateRight`
 */
public class ArrayProblems {

    /**
     * Returns a `String` representation of the input array.
     * Always starts with '[' and ends with ']'; elements are separated by ',' and a space.
     */
    public static String toString(int[] array) {
        String empty = "[";
        if (array.length > 0) {
            empty += array[0];
            for (int i = 1; i < array.length; i++) {
                empty += ", ";
                empty += array[i];
            }
        }
        empty += "]";
        return empty;
    }

    /**
     * Returns a new array containing the input array's elements in reversed order.
     * Does not modify the input array.
     */
    public static int[] reverse(int[] array) {
        int[] newArray = new int[array.length];
        int j = 0;
        for (int i = array.length - 1; i >= 0; i--) {
            newArray[j] = array[i];
            j++;
        }
        return newArray;
    }

    /**
     * Rotates the values in the array to the right.
     */
    public static void rotateRight(int[] array) {
        if (array.length > 0 && array.length != 1) {
            int num = array[0];
            int numB;
            array[0] = array[array.length - 1];
            for (int i = 0; i < array.length - 1; i++) {
                numB = array[i + 1];
                array[i + 1] = num;
                num = numB;
            }
        }
    }
}
