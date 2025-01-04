import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public final class SequenceSmooth {

    private SequenceSmooth() {
        // no code needed here
    }

    /**
     * Smoothes a given {@code Sequence<Integer>}.
     *
     * @param s1
     *            the sequence to smooth
     * @param s2
     *            the resulting sequence
     * @replaces s2
     * @requires |s1| >= 1
     * @ensures <pre>
     * |s2| = |s1| - 1  and
     *  for all i, j: integer, a, b: string of integer
     *      where (s1 = a * <i> * <j> * b)
     *    (there exists c, d: string of integer
     *       (|c| = |a|  and
     *        s2 = c * <(i+j)/2> * d))
     * </pre>
     */
    public static void smooth(Sequence<Integer> s1, Sequence<Integer> s2) {
//
//        Sequence<Integer> newS = s2.newInstance();
//
//        for (int i = 0; i < s1.length() - 1; i++) {
//            if (s1.length() == 0) {
//                newS.add(0, 0);
//
//            }
//            int num1 = s1.entry(i);
//            int num2 = s1.entry(i + 1);
//            int avg = (num1 + num2) / 2;
//            newS.add(i, avg);
//        }
//
//        s2.transferFrom(newS);

//        s2.clear();
        //////////////////////
        if (s1.length() == 1) {
            s2.clear();
        } else {
            long num1 = s1.remove(0); // remove the first from s1
            long num2 = s1.entry(0); // get the second number in s1
            int avg = (int) ((num1 + num2) / 2);
            smooth(s1, s2);
            s2.add(0, avg);
            s1.add(0, (int) num1);

        }

    }

    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        Sequence<Integer> s1 = new Sequence1L<>();
        Sequence<Integer> s2 = new Sequence1L<>();
        s1.add(0, 2);
        s1.add(1, 4);
        s1.add(2, 6);
        s2.add(0, -5);
        s2.add(1, 12);
        out.println("og: " + s1 + "    " + s2);
        smooth(s1, s2);

        out.println(s1);
        out.println(s2);

    }

}
