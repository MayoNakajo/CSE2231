import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Check if a given {@code Sequence<Integer>} is a palindrome.
 *
 * @author Mayo Nakajo
 *
 */
public final class SequencePalindrome {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private SequencePalindrome() {
    }

    /**
     * Construct and return a sequence from a given array.
     *
     * @param args
     *            the array of integer
     * @return the sequence of integer
     * @ensures createFromArgs = [the sequence of integers in args]
     */
    private static Sequence<Integer> createFromArgs(int[] args) {
        assert args != null : "Violation of: args is not null";
        Sequence<Integer> s = new Sequence1L<Integer>();
        for (int x : args) {
            s.add(s.length(), x);
        }
        return s;
    }

    /**
     * Checks if a given {@code Sequence<Integer>} is a palindrome.
     *
     * @param s
     *            the {@code Sequence} to check
     * @return true if the given {@code Sequence} is a palindrome, false
     *         otherwise
     * @ensures isPalindrome = (s = rev(s))
     */
    private static boolean isPalindrome(Sequence<Integer> s, SimpleWriter out) {
        assert s != null : "Violation of: s is not null";

        boolean isP = true;
//
//
//        if (s.length() > 1) { // only check s longer than one integer
//            for (int i = 0; i < s.length() - 1; i++) {
//                if (!s.entry(i).equals(s.entry(s.length() - 1 - i))) {
//                    isP = false;
//                }
//            }
//        }

        boolean rec = true; // to keep track if recursion is necessary
        if (s.length() < 2) { // less than two is always a palindrome
            isP = true;
            rec = false;
        }
        if ((s.length() > 1)) {
            if (!s.entry(0).equals(s.entry(s.length() - 1))) {
                isP = false; //first and last integers are not the same
                rec = false; // no need to recurse
            } else {
                isP = true; // the front and end match
                rec = true;
            }
        }

        if (rec) {
            int num1 = s.remove(0); // shorten the palindrome
            int num2 = s.remove(s.length() - 1);
            isP = isPalindrome(s, out);
            s.add(0, num1); // restore s
            s.add(s.length(), num2);

        }
        return isP;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        final int[][] sequences = { {}, { 1 }, { 2, 2 }, { 3, 4, 3 },
                { 5, 6, 7, 8, 8, 7, 6, 5 }, { 9, 10, 11, 12, 13, 12, 11, 10, 9 },
                { 1, 2 }, { 3, 4, 5 }, { 6, 7, 8, 8, 7, 9 }, { 10, 11, 12, 12, 13, 10 },
                { 14, 15, 16, 17, 15, 14 }, { 6, 7, 8, 18, 8, 7, 9 },
                { 10, 11, 12, 19, 12, 13, 10 }, { 14, 15, 16, 20, 17, 15, 14 }, { 512 },
                { 512, 512 }, { 512, 512, 512 }, { 512, 512, 512, 512 } };
        final boolean[] results = { true, true, true, true, true, true, false, false,
                false, false, false, false, false, false, true, true, true, true };

        for (int i = 0; i < sequences.length; i++) {
            Sequence<Integer> s = createFromArgs(sequences[i]);
            Sequence<Integer> sCopy = createFromArgs(sequences[i]);
            /*
             * Check returned result and parameter restores mode
             */
            boolean correctResult = (isPalindrome(s, out) == results[i]);
            boolean restoredParameter = s.equals(sCopy);
            if (correctResult && restoredParameter) {
                out.print("    Test passed: " + s + " is ");
                if (!results[i]) {
                    out.print("not ");
                }
                out.println("a palindrome");
            } else {
                if (!correctResult) {
                    out.print("*** Test failed: " + sCopy + " is ");
                    if (!results[i]) {
                        out.print("not ");
                    }
                    out.println("a palindrome");
                }
                if (!restoredParameter) {
                    out.println("*** Test failed: " + s
                            + " was not restored to its original value " + sCopy);
                }
            }
            out.println();
        }

        out.close();
    }

}
