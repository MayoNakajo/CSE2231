import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Lets the user test the {@code hashCode(String)} method, by reading text lines
 * from a file (whose name is supplied by the user), and then outputting the
 * distribution of lines into buckets.
 *
 * @author Mayo Nakajo
 *
 */
public final class HashingExploration {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private HashingExploration() {
    }

    /**
     * Computes {@code a} mod {@code b} as % should have been defined to work.
     *
     * @param a
     *            the number being reduced
     * @param b
     *            the modulus
     * @return the result of a mod b, which satisfies 0 <= {@code mod} < b
     * @requires b > 0
     * @ensures <pre>
     * 0 <= mod  and  mod < b  and
     * there exists k: integer (a = k * b + mod)
     * </pre>
     */
    public static int mod(int a, int b) {
        assert b > 0 : "Violation of: b > 0";

        // solution from lab
        int answer = a % b;
        if (answer < 0) {
            answer += b;
        }

        return answer;

    }

    public static void createMap(Map m) {
        m.add('A', 2);
        m.add('B', 2);
        m.add('C', 2);

        m.add('D', 3);
        m.add('E', 3);
        m.add('F', 3);

        m.add('G', 4);
        m.add('H', 4);
        m.add('I', 4);

        m.add('J', 5);
        m.add('K', 5);
        m.add('L', 5);

        m.add('M', 6);
        m.add('N', 6);
        m.add('O', 6);

        m.add('P', 7);
        m.add('Q', 7);
        m.add('R', 7);
        m.add('S', 7);

        m.add('T', 8);
        m.add('U', 8);
        m.add('V', 8);

        m.add('W', 9);
        m.add('X', 9);
        m.add('Y', 9);
        m.add('Z', 9);
    }

    /**
     * Returns a hash code value for the given {@code String}.
     *
     * @param s
     *            the {@code String} whose hash code is computed
     * @return a hash code value for the given {@code String}
     * @ensures hashCode = [hash code value for the given String]
     */
    private static int hashCode(SimpleWriter out, String s) {
        assert s != null : "Violation of: s is not null";

//        int num = 0;
//        for (int i = 0; i < s.length(); i++) {
//            num += s.charAt(i);
//        }
//        return num;
        ////////////////////////
//        int num = 0;
//        for (int i = 0; i < s.length(); i++) {
//            num += Character.getNumericValue(s.charAt(i));
//        }
//        return num;

        Map<Character, Integer> map = new Map1L<>();
        createMap(map);

        int num = 0;
        for (int i = 0; i < s.length(); i++) {
            //out.println(s.charAt(i));
            if (Character.isDigit(s.charAt(i))) {
                //out.println(s.charAt(i));
                num += (s.charAt(i)) - '0';
            } else if (map.hasKey(s.charAt(i))) {
                //out.println(s.charAt(i));
                //out.println(map.value(s.charAt(i)));
                num += map.value(s.charAt(i));
            } else if (map.hasKey(Character.toUpperCase(s.charAt(i)))) {
                //out.println(s.charAt(i));
                //out.println(map.value(Character.toUpperCase(s.charAt(i))));
                num += map.value(((Character.toUpperCase(s.charAt(i)))));
            }

        }
        return num;
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
        /*
         * Get hash table size and file name.
         */
        out.print("Hash table size: ");
        int hashTableSize = in.nextInteger();
        out.print("Text file name: ");
        String textFileName = in.nextLine();
        /*
         * Set up counts and counted. All entries in counts are automatically
         * initialized to 0.
         */
        int[] counts = new int[hashTableSize];
        Set<String> counted = new Set1L<String>();
        /*
         * Get some lines of input, hash them, and record counts.
         */
        SimpleReader textFile = new SimpleReader1L(textFileName);
        while (!textFile.atEOS()) {
            String line = textFile.nextLine();
            if (!counted.contains(line)) {
                int bucket = mod(hashCode(out, line), hashTableSize);
                counts[bucket]++;
                counted.add(line);
            }
        }
        textFile.close();
        /*
         * Report results.
         */
        out.println();
        out.println("Bucket\tHits\tBar");
        out.println("------\t----\t---");
        for (int i = 0; i < counts.length; i++) {
            out.print(i + "\t" + counts[i] + "\t");
            for (int j = 0; j < counts[i]; j++) {
                out.print("*");
            }
            out.println();
        }
        out.println();
        out.println("Total:\t" + counted.size());
        in.close();
        out.close();
    }

}
