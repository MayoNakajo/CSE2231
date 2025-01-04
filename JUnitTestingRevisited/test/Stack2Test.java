import components.stack.Stack;
import components.stack.Stack2;

/**
 * Customized JUnit test fixture for {@code Stack1L}.
 */
public class Stack2Test extends StackTest {

    @Override
    protected final Stack<String> constructorTest() {
        Stack<String> st = new Stack2<>();

        return st;
    }

    @Override
    protected final Stack<String> constructorRef() {
        Stack<String> st = new Stack2<>();

        return st;

    }

}
