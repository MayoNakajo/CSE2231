import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.stack.Stack;
import components.stack.Stack1L;

public final class SequenceOnStack {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private SequenceOnStack() {
    }

    /**
     * Shifts entries between {@code leftStack} and {@code rightStack}, keeping
     * reverse of the former concatenated with the latter fixed, and resulting
     * in length of the former equal to {@code newLeftLength}.
     *
     * @param <T>
     *            type of {@code Stack} entries
     * @param leftStack
     *            the left {@code Stack}
     * @param rightStack
     *            the right {@code Stack}
     * @param newLeftLength
     *            desired new length of {@code leftStack}
     * @updates leftStack, rightStack
     * @requires <pre>
     * 0 <= newLeftLength  and
     * newLeftLength <= |leftStack| + |rightStack|
     * </pre>
     * @ensures <pre>
     * rev(leftStack) * rightStack = rev(#leftStack) * #rightStack  and
     * |leftStack| = newLeftLength}
     * </pre>
     */
    private static void setLengthOfLeftStack(Stack<Integer> leftStack,
            Stack<Integer> rightStack, int newLeftLength) {

        while (leftStack.length() != newLeftLength) {
            int num = leftStack.pop();
            rightStack.push(num);
        }

    }

    
    

    
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        Stack<Integer> st1 = new Stack1L<>();
        Stack<Integer> st2 = new Stack1L<>();

        st1.push(2);
        st1.push(3);
        st1.push(4);

        st2.push(0);
        st2.push(1);
        st2.push(2);

        setLengthOfLeftStack(st1, st2, 1);

        out.println(st1 + " " + st2);

    }
}
