import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Simple HelloWorld program (clear of Checkstyle and SpotBugs warnings).
 *
 * @author Mayo Nakajo and Cara Coy
 */
public final class TagCloudJCF {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloudJCF() {
        // no code needed here
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
     * @param set
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
            HashSet<Character> set) {
        assert text != null : "Violation of: text is not null";
        assert set != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";
        String result = "";
        boolean stop = false;
        char c;
        for (int i = position; i < text.length() && !stop; i++) {
            c = text.charAt(i);
            // c is not a separator
            if (!set.contains(c)) {
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
            if (Character.isLowerCase(c)) {
                // c is already lower case, append to result
                result = result + Character.toString(c);
            } else {
                // change c to lower case and append to result
                result = result + Character.toString(Character.toLowerCase(c));
            }
        }
        return result;
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
    private static void separatorSet(String str, HashSet<Character> set) {
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
    public static void createMap(HashMap<String, Integer> map, String word) {
        // if map does not have word, add
        if (!map.containsKey(word)) {
            map.put(word, 1);
        } else {
            // map already contains word, increase count
            Integer val = map.remove(word);
            val++;
            map.put(word, val);
        }
    }

    /**
     * Compare {@code String}s in numerical order.
     */
    private static final class CompareValue
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> pair1,
                Map.Entry<String, Integer> pair2) {
            // compare the values
            int c = pair2.getValue().compareTo(pair1.getValue());
            // if the values are the same, compare the keys
            if (c == 0) {
                c = pair1.getKey().compareTo(pair2.getKey());
            }
            return c;

        }
    }

    /**
     * Compare {@code String}s in lexicographic order.
     */
    private static final class CompareKey
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> pair1,
                Map.Entry<String, Integer> pair2) {
            // compare keys
            int c = pair1.getKey().compareTo(pair2.getKey());
            // if keys are the same, compare the values
            if (c == 0) {
                c = pair1.getValue().compareTo(pair2.getValue());
            }
            return c;

        }
    }

    /**
     * Prints html header tags.
     *
     * @param out
     *            output stream
     * @param input
     *            title of the text
     * @param wordNum
     *            number of words to include in the tag cloud
     * @ensures all header tags are printed to the file.
     *
     */
    public static void outputHeader(PrintWriter out, String input, int wordNum) {
        // output html header with number of words and the title of the text
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
    }

    /**
     * Generates the font size for the given word.
     *
     * @param pair
     *            pair to generate font sixe for
     * @param maxCount
     *            max number a word appears in the text
     * @param minCt
     *            min number a word appears in the text
     * @return font size associated with the word in the pair
     * @ensures the font size is within 11 and 48 and is a relative size to the
     *          word count
     */
    public static int fontSize(Map.Entry<String, Integer> pair, int maxCount, int minCt) {
        int result = 0;
        final double min = 11, max = 48, count = pair.getValue();
        // equation for font size given word count
        result = (int) Math.round((count * 1.0 / maxCount) * (max - min) + min);
        return result;
    }

    /**
     * Prints out each span line.
     *
     * @param out
     *            output stream
     * @param wordNum
     *            number of words to include in the tag cloud
     * @param map
     *            contains all words in the file
     * @ensures wordNum number of words are all printed out in the span line
     */
    public static void span(PrintWriter out, int wordNum, HashMap<String, Integer> map) {
        List<Map.Entry<String, Integer>> listAll = new ArrayList<>();

        Set<Map.Entry<String, Integer>> set = map.entrySet();
        // add all elements in the map to the temp list
        for (Map.Entry<String, Integer> entry : set) {
            listAll.add(entry);
        }
        Comparator<Map.Entry<String, Integer>> cs = new CompareValue();
        // sort the list in word count order
        listAll.sort(cs);
        int i = 0;
        List<Map.Entry<String, Integer>> list = new ArrayList<>();
        // only get wordNum amount of entries in the list to generate the tag cloud
        while (i < wordNum) {
            list.add(listAll.getFirst());
            listAll.removeFirst();
            i++;
        }
        int max = 0;
        int min = 0;
        // find max and min number of times a word appearers in the top wordNums
        if (list.size() > 1) {
            max = list.getFirst().getValue();
            min = list.getLast().getValue();
        }

        Comparator<Map.Entry<String, Integer>> cs2 = new CompareKey();
        // sort alphabetically
        list.sort(cs2);
        // print span for each entry in the list
        while (list.size() > 0) {
            Map.Entry<String, Integer> pair = list.removeFirst();
            out.println("<span style\"cursor:default\" class=\"f"
                    + fontSize(pair, max, min) + "\" title=\"count: " + pair.getValue()
                    + "\">" + pair.getKey() + "</span>");
        }

    }

    /**
     * Outputs the closing tags.
     *
     * @param out
     *            output stream
     *
     * @ensures html closing tags are printed
     */
    public static void outputFooter(PrintWriter out) {
        // output html footer
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter input file name: ");
        String fileName = "";
        BufferedReader inFile = null;
        while (inFile == null) {
            try {
                fileName = in.readLine();
                inFile = new BufferedReader(new FileReader(fileName));
            } catch (IOException e) {
                System.err.println(e + " is an invalid input.");
            }
        }

        System.out.print("Enter output file name: ");
        String output = "";
        PrintWriter out = null;
        while (out == null) {
            try {
                output = in.readLine();
                out = new PrintWriter(new BufferedWriter(new FileWriter(output)));

            } catch (IOException e) {
                System.err.println(e + " is an invalid input");
            }
        }

        System.out.println("Enter number of words to include in generated cloud tag: ");

        int wordNum = 0;
        try {
            // parse string to integer value
            try {
                wordNum = Integer.parseInt(in.readLine());
            } catch (IOException e) {
                System.err.println(e + " is an invalid input");
            }
        } catch (NumberFormatException e) {
            System.err.println(e + " must be a number.");
        }
        // create separator set
        HashSet<Character> set = new HashSet<Character>();
        separatorSet(" \t\n\r,-.!?[];:'*/()0123456789", set);
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        String line = null;
        try {
            // read next line
            line = inFile.readLine();
        } catch (IOException e) {
            System.err.println("Error reading line");
        }

        while (line != null) {
            int position = 0;
            while (position < line.length()) {
                // a single word in the input file
                String token = nextWordOrSeparator(line, position, set);
                // if it is a word and not a separator
                if (!set.contains(token.charAt(0))) {
                    String word = toLowercase(token);
                    if (!map.containsKey(word)) {
                        map.put(word, 1);
                    } else {
                        // increase count associated with the word
                        int num = map.get(word) + 1;
                        map.remove(word);
                        map.put(word, num);
                    }

                }
                // increase position by length of token(word)
                position += token.length();
            }
            try {
                // read next line
                line = inFile.readLine();
            } catch (IOException e) {
                System.err.println("Cannot read line");
            }
        }
        // print to the output file
        outputHeader(out, fileName, wordNum);
        span(out, wordNum, map);
        outputFooter(out);

    }

}
