import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;

/**
 * Simple HelloWorld program (clear of Checkstyle and SpotBugs warnings).
 *
 * @author Mayo Nakajo and Cara Coy
 */
public final class TagCloud {

    /**
     * Compare {@code String}s in numerical order.
     */
    private static final class CompareValue
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> pair1,
                Map.Pair<String, Integer> pair2) {
            return pair2.value().compareTo(pair1.value());
        }
    }

    /**
     * Compare {@code String}s in lexicographic order.
     */
    private static final class CompareKey
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Pair<String, Integer> pair1, Pair<String, Integer> pair2) {
            return pair1.key().compareTo(pair2.key());
        }
    }

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloud() {
        // no code needed here
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param set
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    private static void separatorSet(String str, Set<Character> set) {
        assert str != null : "Violation of: str is not null";
        // set to add str to
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // if c is not in newSet, add
            if (!set.contains(c)) {
                set.add(c);
            }
        }

    }

    /**
     * Calculates font size for given pair.
     *
     * @param pair
     *            Word and number of counts appeared in the text
     * @param maxCount
     *            max number of times a word appeared in a text
     * @requires pair must not be null
     * @ensures result is between 11 and 48
     * @return font size for the pair
     */
    public static int fontSize(Map.Pair<String, Integer> pair, int maxCount) {
        int result = 0;
        // source for font formula
        // https://stackoverflow.com/questions/3717314/what-is-the-formula-to-c
        // alculate-the-font-size-for-tags-in-a-tagcloud

        final double min = 11, max = 48, count = pair.value();
        result = (int) Math.round((count * 1.0 / maxCount) * (max - min) + min);
        return result;
    }

    /**
     * Print out each span line.
     *
     * @param sm
     *            SortingMacine containing pairs of words and their counts
     * @param out
     *            output stream into user given file
     * @param max
     *            max number of times a word appeared in a text
     * @param min
     *            min number of times a word appeared in a text
     * @requires sm is not empty
     * @ensures prints out each span line to the output file
     */
    public static void printSpan(SortingMachine<Pair<String, Integer>> sm,
            SimpleWriter out, int max, int min) {
        sm.changeToExtractionMode();

        // remove a pair from the sorting machine
        // print span line associated with pair
        while (sm.size() > 0) {
            Pair<String, Integer> pair = sm.removeFirst();
            out.println("<span style\"cursor:default\" class=\"f" + fontSize(pair, max)
                    + "\" title=\"count: " + pair.value() + "\">" + pair.key()
                    + "</span>");

        }
    }

    /**
     * Outputs the header with the name of text input by user.
     *
     * @param output
     *            output stream
     * @param input
     *            name of the input file
     * @param wordNum
     *            number of words to include in the tag cloud
     * @param max
     *            max number of times a word appeared in a text
     * @param pair
     *            SortingMachine containing pairs of the word and the number of
     *            times the word appears in the text
     * @param min
     *            min number of times a word appeared in a text
     * @requires inputName /= ""
     * @ensures prints out the header with the inputName as the header
     */
    public static void outputHeader(String input, String output, int wordNum,
            SortingMachine<Pair<String, Integer>> pair, int max, int min) {
        SimpleWriter out = new SimpleWriter1L(output);
        SimpleReader in = new SimpleReader1L(input);
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Top " + wordNum + " words in " + input + "</title>");
        out.println("<link href=\"https://cse22x1.engineering.osu.edu/2231/web-sw2"
                + "/assignments/projects/tag-cloud-generator/data/tagcloud.css\""
                + " rel=\"stylesheet\" type=\"text/css\">");
        out.println(
                "<link href=\"tagcloud.css\" rel=\"stylesheet\" type=text" + "/css\">");
        out.println("</head>");
        out.println("<body data-new-gr-c-s-check-loaded=\"14.1207.0\" data-gr-ext-"
                + "installed>");
        out.println("<h2>Top " + wordNum + " words in " + input + "</h2>");
        out.println("<hr>");
        out.println("<div class=\"cdiv\">");
        out.println("<p class=\"cbox\">");
        printSpan(pair, out, max, min);
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
        out.close();
        in.close();
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        String result = "";
        boolean stop = false;
        char c;
        for (int i = position; i < text.length() && !stop; i++) {
            c = text.charAt(i);
            // c is not a separator
            if (!separators.contains(c)) {
                // concatenate to result
                result += c;
            } else {
                if (!result.isEmpty()) {
                    stop = true;
                } else {
                    result += c;
                    // end of word
                    stop = true;
                }
            }

        }

        return result;
    }

    /**
     * Creates a map of <String, Integer>.
     *
     * @param map
     *            map to add the word and number of times it appears
     *
     * @param word
     *            key to add to map
     * @requires word /= ""
     * @ensures map = #map * <word, num>
     */
    public static void createMap(Map<String, Integer> map, String word) {
        // if map does not have word, add
        if (!map.hasKey(word)) {
            map.add(word, 1);

        } else {
            // map already contains word, increase count
            Map.Pair<String, Integer> p = map.remove(word);
            int num = p.value();
            num++;
            map.add(word, num);
        }

    }

    /**
     * Takes a string input and returns it in all lower case letters.
     *
     * @param word
     *            string to convert to all lower case letters
     * @requires word /= ""
     * @ensures word is returned all lower case
     * @return word in lower case
     */
    public static String toLowercase(String word) {
        String result = "";
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            // append if char is already lower case
            if (Character.isLowerCase(c)) {
                result = result + Character.toString(c);
            } else {
                // change upper case to lower case then append
                result = result + Character.toString(Character.toLowerCase(c));
            }
        }
        return result;
    }

    /**
     *
     * @param sm
     *            SortingMachine containing pairs
     * @param map
     *            Map containing words and number of how many times they appear
     * @param n
     *            how many words to include in the SortingMachine
     * @param sm2
     *            SortingMachine that sorts the Strings in alphabetical order
     * @requires |map| > 0 && n > 0
     * @ensures sm contains all entries in map in value numerical order && sm2
     *          contains n entries in alphabetical order
     * @return max number of times a word appears in a text
     */
    public static int addToSm(SortingMachine<Pair<String, Integer>> sm,
            Map<String, Integer> map, int n, SortingMachine<Pair<String, Integer>> sm2) {

        // remove pairs of words and counts into sorting machine
        while (map.size() > 0) {
            Map.Pair<String, Integer> pair = map.removeAny();
            sm.add(pair);
        }

        sm.changeToExtractionMode();
        // removes in order of count size
        Pair<String, Integer> p = sm.removeFirst();
        // get max count
        int max = p.value();
        sm2.add(p);
        int i = 0;
        // because we remove max and min do n - 2
        // get n - 2 elements into sm2
        while (i < n - 2) {
            Pair<String, Integer> pair = sm.removeFirst();
            i++;
            sm2.add(pair);
        }
        return max;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {

        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();

        out.print("Enter input file name: ");
        String fileName = in.nextLine();
        SimpleReader fileIn = new SimpleReader1L(fileName);
        String input = "";
        while (!fileIn.atEOS()) {
            input += fileIn.nextLine();
            input += " ";
        }
        out.print("Enter output file name: ");
        String output = in.nextLine();
        out.println("Enter number of words to include in generated cloud tag: ");
        int wordNum = in.nextInteger();

        // create separator set
        Set<Character> set = new Set1L<>();
        separatorSet(" \t\n\r,-.!?[];:'*/()0123456789", set);

        Map<String, Integer> map = new Map1L<>();
        int position = 0;
        while (position < input.length()) {
            // a single word in the input file
            String token = nextWordOrSeparator(input, position, set);
            // if it is a word and not a separator
            if (!set.contains(token.charAt(0))) {
                String word = toLowercase(token);
                createMap(map, word);
                // createQueue(q, token);
                // change to sorting machine : sortQ(q);
            }
            // parse through the input
            position += token.length();
        }

        Comparator<Pair<String, Integer>> cs = new CompareValue();
        SortingMachine<Map.Pair<String, Integer>> sm = new SortingMachine1L<>(cs);
        Comparator<Pair<String, Integer>> cs2 = new CompareKey();
        SortingMachine<Pair<String, Integer>> sm2 = new SortingMachine1L<>(cs2);
        int max = addToSm(sm, map, wordNum, sm2);
        // last element in sm is the min count because it is sorted in count
        // size greatest to least
        Pair<String, Integer> pair = sm.removeFirst();
        int min = pair.value();
        sm2.add(pair);
        // print to file
        outputHeader(fileName, output, wordNum, sm2, max, min);
        // close streams
        fileIn.close();
        out.close();
        in.close();
    }

}
