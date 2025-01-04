import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.stack.Stack;

/**
 * JUnit test fixture for {@code Stack<String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class StackTest {

    /**
     * Invokes the appropriate {@code Stack} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new stack
     * @ensures constructorTest = <>
     */
    protected abstract Stack<String> constructorTest();

    /**
     * Invokes the appropriate {@code Stack} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new stack
     * @ensures constructorRef = <>
     */
    protected abstract Stack<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Stack<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsTest = [entries in args]
     */
    private Stack<String> createFromArgsTest(String... args) {
        Stack<String> stack = this.constructorTest();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    /**
     *
     * Creates and returns a {@code Stack<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsRef = [entries in args]
     */
    private Stack<String> createFromArgsRef(String... args) {
        Stack<String> stack = this.constructorRef();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    @Test
    public void constructorTest1() {
        Stack<String> s = this.constructorTest();
        Stack<String> sExp = this.constructorRef();

        assertEquals(sExp, s);
    }

    /**
     * Test case for push with one string to empty Stack
     */
    @Test
    public void pushTest1() {
        Stack<String> s = this.createFromArgsTest();
        Stack<String> sExp = this.createFromArgsRef("a");

        s.push("a");

        assertEquals(sExp, s);

    }

    /**
     * Test case for push with one string to non empty stack
     */
    @Test
    public void pushTest2() {
        Stack<String> s = this.createFromArgsTest("a");
        Stack<String> sExp = this.createFromArgsRef("b", "a");

        s.push("b");

        assertEquals(sExp, s);

    }

    /**
     * Test case for push with two strings to empty stack
     */
    @Test
    public void pushTest3() {
        Stack<String> s = this.createFromArgsTest();
        Stack<String> sExp = this.createFromArgsRef("a", "b");

        s.push("b");
        s.push("a");

        assertEquals(sExp, s);

    }

    /**
     * Test case for push with two strings to empty stack
     */
    @Test
    public void pushTest4() {
        Stack<String> s = this.createFromArgsTest("c");
        Stack<String> sExp = this.createFromArgsRef("a", "b", "c");

        s.push("b");
        s.push("a");

        assertEquals(sExp, s);

    }

    /**
     * Test case for pop with one string to empty stack
     */
    @Test
    public void popTest1() {
        Stack<String> s = this.createFromArgsTest("a");
        Stack<String> sExp = this.createFromArgsRef();

        String pop = s.pop();

        assertEquals("a", pop);
        assertEquals(sExp, s);

    }

    /**
     * Test case for pop with one string to non empty stack
     */
    @Test
    public void popTest2() {
        Stack<String> s = this.createFromArgsTest("a", "b");
        Stack<String> sExp = this.createFromArgsRef("b");

        String pop = s.pop();

        assertEquals("a", pop);
        assertEquals(sExp, s);

    }

    /**
     * Test case for pop with two strings to empty stack
     */
    @Test
    public void popTest3() {
        Stack<String> s = this.createFromArgsTest("a", "b");
        Stack<String> sExp = this.createFromArgsRef("a", "b");

        String pop1 = s.pop();
        String pop2 = s.pop();

        String pop3 = sExp.pop();
        String pop4 = sExp.pop();

        assertEquals(pop3, pop1);
        assertEquals(pop4, pop2);
        assertEquals(sExp, s);

    }

    /**
     * Test case for pop with two strings to non empty stack
     */
    @Test
    public void popTest4() {
        Stack<String> s = this.createFromArgsTest("a", "b", "c", "d");
        Stack<String> sExp = this.createFromArgsRef("a", "b", "c", "d");

        String pop1 = s.pop();
        String pop2 = s.pop();

        String pop3 = sExp.pop();
        String pop4 = sExp.pop();

        assertEquals(pop3, pop1);
        assertEquals(pop4, pop2);
        assertEquals(sExp, s);

    }

    /**
     * Test case for length with empty stack
     */
    @Test
    public void lengthTest1() {
        Stack<String> s = this.createFromArgsTest();
        Stack<String> sExp = this.createFromArgsRef();

        assertEquals(sExp.length(), s.length());
        assertEquals(sExp, s);
    }

    /**
     * Test case for length with stack of length 1
     */
    @Test
    public void lengthTest2() {
        Stack<String> s = this.createFromArgsTest("a");
        Stack<String> sExp = this.createFromArgsRef("a");

        assertEquals(sExp.length(), s.length());
        assertEquals(sExp, s);
    }

    /**
     * Test case for length with stack of length 2
     */
    @Test
    public void lengthTest3() {
        Stack<String> s = this.createFromArgsTest("a", "b");
        Stack<String> sExp = this.createFromArgsRef("a", "b");

        assertEquals(sExp.length(), s.length());
        assertEquals(sExp, s);
    }

    /**
     * Test case for length with stack of length 5
     */
    @Test
    public void lengthTest4() {
        Stack<String> s = this.createFromArgsTest("a", "b", "c", "d", "e");
        Stack<String> sExp = this.createFromArgsRef("a", "b", "c", "d", "e");

        assertEquals(sExp.length(), s.length());
        assertEquals(sExp, s);
    }
}
