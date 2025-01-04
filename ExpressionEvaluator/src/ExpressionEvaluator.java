import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program calculates the value of an expression consisting of numbers,
 * arithmetic operators, and parentheses.
 *
 * @author Put your name here
 *
 */
public final class ExpressionEvaluator {

    /**
     * Base used in number representation.
     */
    private static final int RADIX = 10;

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ExpressionEvaluator() {
    }

    /**
     * Evaluates a digit and returns its value.
     *
     * @param source
     *            the {@code StringBuilder} that starts with a digit
     * @return value of the digit
     * @updates source
     * @requires 1 < |source| and [the first character of source is a digit]
     * @ensures <pre>
     * valueOfDigit = [value of the digit at the start of #source]  and
     * #source = [digit string at start of #source] * source
     * </pre>
     */
    private static int valueOfDigit(StringBuilder source) {
        assert source != null : "Violation of: source is not null";

        // rule: digit --> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |

        char c = source.charAt(0);
        source.deleteCharAt(0);
        return Character.digit(c, RADIX);
    }

    /**
     * Evaluates a digit sequence and returns its value.
     *
     * @param source
     *            the {@code StringBuilder} that starts with a digit-seq string
     * @return value of the digit sequence
     * @updates source
     * @requires <pre>
     * [a digit-seq string is a proper prefix of source, which
     * contains a character that is not a digit]
     * </pre>
     * @ensures <pre>
     * valueOfDigitSeq =
     *   [value of longest digit-seq string at start of #source]  and
     * #source = [longest digit-seq string at start of #source] * source
     * </pre>
     */
    private static int valueOfDigitSeq(StringBuilder source) {
        assert source != null : "Violation of: source is not null";

        // digit-seq --> digit {digit}
        int value = valueOfDigit(source);
        while (Character.isDigit(source.charAt(0))) {
            int dig = valueOfDigit(source);
            value = value * RADIX + dig;
        }

        return value;
    }

    /**
     * Evaluates a factor and returns its value.
     *
     * @param source
     *            the {@code StringBuilder} that starts with a factor string
     * @return value of the factor
     * @updates source
     * @requires <pre>
     * [a factor string is a proper prefix of source, and the longest
     * such, s, concatenated with the character following s, is not a prefix
     * of any factor string]
     * </pre>
     * @ensures <pre>
     * valueOfFactor =
     *   [value of longest factor string at start of #source]  and
     * #source = [longest factor string at start of #source] * source
     * </pre>
     */
    private static int valueOfFactor(StringBuilder source) {
        assert source != null : "Violation of: source is not null";

        int value = 0;
        if (source.charAt(0) == '(') {
            source.deleteCharAt(0);
            value = valueOfExpr(source);
        } else {
            value = valueOfDigitSeq(source);
        }
        return value;
    }

    /**
     * Evaluates a term and returns its value.
     *
     * @param source
     *            the {@code StringBuilder} that starts with a term string
     * @return value of the term
     * @updates source
     * @requires <pre>
     * [a term string is a proper prefix of source, and the longest
     * such, s, concatenated with the character following s, is not a prefix
     * of any term string]
     * </pre>
     * @ensures <pre>
     * valueOfTerm =
     *   [value of longest term string at start of #source]  and
     * #source = [longest term string at start of #source] * source
     * </pre>
     */
    private static int valueOfTerm(StringBuilder source) {
        assert source != null : "Violation of: source is not null";

        int value = valueOfFactor(source);

        System.out.println("source valueOfTerm: " + source);
        while (source.charAt(0) == '*' || source.charAt(0) == '/') {
            char op = source.charAt(0);
            source.deleteCharAt(0);
            int nextTerm = valueOfFactor(source);
            if (op == '*') {
                value *= nextTerm;
            } else {
                value /= nextTerm;
            }
        }
        System.out.println("value valueOfTerm: " + value);
        return value;

    }

    /**
     * Evaluates an expression and returns its value.
     *
     * @param source
     *            the {@code StringBuilder} that starts with an expr string
     * @return value of the expression
     * @updates source
     * @requires <pre>
     * [an expr string is a proper prefix of source, and the longest
     * such, s, concatenated with the character following s, is not a prefix
     * of any expr string]
     * </pre>
     * @ensures <pre>
     * valueOfExpr =
     *   [value of longest expr string at start of #source]  and
     * #source = [longest expr string at start of #source] * source
     * </pre>
     */
    public static int valueOfExpr(StringBuilder source) {
        assert source != null : "Violation of: source is not null";

        // goes down to get digit, removes from source
        int value = valueOfTerm(source);

        // next in source is operator
        while (source.charAt(0) == '+' || source.charAt(0) == '-') {
            System.out.println("source valueOfExpr: " + source);
            char op = source.charAt(0);
            source.deleteCharAt(0);
            int nextTerm = valueOfTerm(source);
            if (op == '+') {
                value += nextTerm;
            } else {
                value -= nextTerm;
            }
        }

        System.out.println("value valueOfExpr: " + value);
        return value;
    }

    /**
     * Evaluates a Boolean expression and returns its value.
     *
     * @param tokens
     *            the {@code Queue<String>} that starts with a bool-expr string
     * @return value of the expression
     * @updates tokens
     * @requires [a bool-expr string is a prefix of tokens]
     * @ensures <pre>
     * valueOfBoolExpr =
     *   [value of longest bool-expr string at start of #tokens]  and
     * #tokens = [longest bool-expr string at start of #tokens] * tokens
     * </pre>
     */
    public static boolean valueOfBoolExpr(Queue<String> tokens) {
        /**
         * bool - exp --> T --> F --> NOT (bool - exp) --> ( bool - exp binary -
         * op bool - exp ) --> ( bool-exp binary-op bool-exp ) --> AND | OR
         */
        boolean value = true;
        String first = tokens.dequeue();
        if (first.equals("T")) {
            value = true;
        } else if (first.equals("F")) {
            value = false;
        } else if (first.equals("NOT")) {
            tokens.dequeue(); // remove parenthesis
            value = !valueOfBoolExpr(tokens);
            tokens.dequeue(); // remove parenthesis
        } else if (first.equals("NOT")) {
            tokens.dequeue(); // remove parenthesis
            value = !valueOfBoolExpr(tokens);
            tokens.dequeue(); // remove parenthesis
        } else { // first is a parenthesis
            value = valueOfBoolExpr(tokens);
            String op = tokens.dequeue();
            if (op.equals("AND")) {
                value = value && valueOfBoolExpr(tokens);

            } else {
                value = value || valueOfBoolExpr(tokens);
            }
        }
        return value;

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
        out.print("Enter an expression followed by !: ");
        String expr = in.nextLine();
        while (expr.length() > 0) {
            /*
             * Parse and evaluate the expression after removing all white space
             * (spaces and tabs) from the user input.
             */
            int value = valueOfExpr(new StringBuilder(expr.replaceAll("[ \t]", "")));
            out.println(expr.substring(0, expr.length() - 1) + " = " + value);
            out.print("Enter an expression followed by !: ");
            expr = in.nextLine();
        }

        Queue<String> tokens = new Queue1L<>();
        //tokens.enqueue("NOT");
        //tokens.enqueue("(");
        //tokens.enqueue("F");
        //tokens.enqueue("AND");
        //tokens.enqueue("T");
        //tokens.enqueue(")");
//        tokens.enqueue("T");
//        tokens.enqueue("OR");
//        tokens.enqueue("F");
//        tokens.enqueue("### END OF INPUT ###");
//        out.println(valueOfBoolExpr(tokens));
        out.println(tokens);

        in.close();
        out.close();
    }

}
