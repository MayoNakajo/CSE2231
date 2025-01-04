import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class IntegerAverage {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private IntegerAverage() {
    }

    /**
     * Put a short phrase describing the static method myMethod here.
     */
    private static void myMethod() {
        /*
         * Put your code for myMethod here
         */
    }

    /**
     * Returns the integer average of two given {@code int}s.
     *
     * @param j
     *            the first of two integers to average
     * @param k
     *            the second of two integers to average
     * @return the integer average of j and k
     * @ensures average = (j+k)/2
     */
    public static int average(int j, int k) {
        int avg = 0;

        if (j % 2 == 0 && k % 2 == 0) {
            avg = (j / 2) + (k / 2);
        } else if ((j % 2 != 0) && (k % 2 == 0) || (j % 2 == 0) && (k % 2 != 0)) {
            avg = (j / 2) + (k / 2);
        } else {
            avg = (j / 2) + (k / 2) + 1;
        }

        return avg;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        int num1 = 5;
        int num2 = 9;

        int avg = average(num1, num2);

        out.println(avg);

        in.close();
        out.close();
    }

}
