import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Input a file and the name of the output file and creates an html with a table
 * of the words in alphabetical order and how many times it appears in the text.
 *
 * @author Mayo Nakajo
 *
 */
public final class WordCounter {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private WordCounter() {
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param charSet
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    private static void generateElements(String str, Set<Character> charSet) {
        assert str != null : "Violation of: str is not null";
        assert charSet != null : "Violation of: charSet is not null";
        // set to add str to
        Set<Character> newSet = new Set1L<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // if c is not in newSet, add
            if (!newSet.contains(c)) {
                newSet.add(c);
            }
        }
        charSet.transferFrom(newSet);

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
     * Adds word to q.
     *
     * @param q
     *            q of Strings to add word to
     * @param word
     *            String to add to q
     * @updates q
     * @requires q /= <>, word /= ""
     * @ensures q = #q * word
     */
    public static void createQueue(Queue<String> q, String word) {
        // enqueue word to q
        q.enqueue(word);
    }

    /**
     * Sorts q alphabetically.
     *
     * @param q
     *            q of Strings to sort
     * @requires q /= <>
     * @ensures q is sorted alphabetically
     */
    public static void sortQ(Queue<String> q) {
        Queue<String> temp = q.newInstance();
        temp.transferFrom(q);

        while (temp.length() > 0) {
            String min = removeMin(temp);
            q.enqueue(min);
        }

    }

    /**
     * Removes the min in q.
     *
     * @param q
     *            Queue of words
     * @requires q /= <>
     * @return the min String in q
     * @ensures prints out the header with the inputName as the header
     */
    public static String removeMin(Queue<String> q) {
        assert q != null : "Violation of: q is not null";
        //assert order != null : "Violation of: order is not null";

        Queue<String> temp = q.newInstance();
        String min = q.front();
        //iterate through all elements in q
        for (String s : q) {
            // checks if s is smaller than current min
            if (s.compareToIgnoreCase(min) < 0) {
                min = s;
            }
        }

        // checks if s is smaller than current min

        // if s is not min, add to temp
        for (String s : q) {
            if (!s.equals(min)) {
                temp.enqueue(s);
            }
        }
        q.transferFrom(temp);
        return min;

    }

    /**
     * Outputs the header with the name of text input by user.
     *
     * @param out
     *            output stream
     * @param inputName
     *            name of the input file
     * @requires inputName /= ""
     * @ensures prints out the header with the inputName as the header
     */
    public static void outputHeader(SimpleWriter out, String inputName) {
        // header for html
        out.println("<html>");
        out.println("<style> table, th, td { border:1px solid black;}</style>");
        out.println("<body>");
        out.println("<h1>Words Counted in " + inputName + "</h1>");

    }

    /**
     * Creates a table using the values from the map of words and counts.
     *
     * @param out
     *            output stream
     * @param q
     *            Queue of words in alphabetical order
     * @param map
     *            Map of all the words in q, with corresponding key with how
     *            many times it appears in q
     * @requires q /= <> and map /= <>
     * @ensures prints out a table from the map values
     */
    public static void createTable(SimpleWriter out, Queue<String> q,
            Map<String, Integer> map) {
        // setting up table
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>Words</th>");
        out.println("<th>Counts</th>");
        out.println("</tr>");
        int length = q.length();
        for (int i = 0; i < length; i++) {
            // dequeue alphabetically
            String word = q.dequeue();
            if (map.hasKey(word)) {
                // remove the pair from the map and get key
                Map.Pair<String, Integer> pair = map.remove(word);
                int ct = pair.value();
                out.println("<tr>");
                out.println("<td>" + word + " </td>");
                out.println("<td>" + ct + " </td>");
                out.println("</tr>");
            }

        }
        // closing tags for table, body, and html
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
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

        Set<Character> separatorSet = new Set1L<>();
        final String separatorStr = " \t,. -";
        // create separatorSet
        generateElements(separatorStr, separatorSet);
        // name of file to read
        out.print("Enter input file: ");
        String file = in.nextLine();
        SimpleReader fileIn = new SimpleReader1L(file);
        String input = "";
        // create string from the contents of fileIn
        while (!fileIn.atEOS()) {
            input += fileIn.nextLine();
            input += " ";
        }
        // creates file for html
        out.println("Enter output file: ");
        String output = in.nextLine();
        SimpleWriter outFile = new SimpleWriter1L(output + ".html");

        Map<String, Integer> map = new Map1L<>();
        Queue<String> q = new Queue1L<>();

        int position = 0;
        while (position < input.length()) {
            // a single word in the input file
            String token = nextWordOrSeparator(input, position, separatorSet);
            // if it is a word and not a separator
            if (!separatorSet.contains(token.charAt(0))) {
                createMap(map, token);
                createQueue(q, token);
                sortQ(q);
            }
            // parse through the input
            position += token.length();
        }

        outputHeader(outFile, file);
        createTable(outFile, q, map);

        // close input/output streams
        in.close();
        fileIn.close();
        outFile.close();
        out.close();

    }

}
