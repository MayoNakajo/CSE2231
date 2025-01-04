import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Put your name here
 *
 */

public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    @Test
    public final void testAdd() {
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsTest("a", "b");
        s.add("a");
        s.add("b");
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddEmpty() {

        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsTest();

        s.add("");

        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemove() {

        Set<String> s = this.createFromArgsTest("a", "b");
        Set<String> sExpected = this.createFromArgsTest("b");

        s.remove("a");

        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveAny() {

        Set<String> s = this.createFromArgsTest("a");
        Set<String> sExpected = this.createFromArgsTest();

        String x = s.removeAny();

        assertEquals("a", x);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveAnyTwo() {

        Set<String> s = this.createFromArgsTest("a", "b");

        String x = s.removeAny();
        boolean aOrB = false;
        if (x.equals("a") || x.equals("b")) {
            aOrB = true;
        }
        assertEquals(true, aOrB);
    }

    @Test
    public final void testContainsTrue() {

        Set<String> s = this.createFromArgsTest("a");

        String x = s.removeAny();
        boolean hasX = false;
        if (x == "a") {
            hasX = true;
        }
        assertEquals(true, hasX);
    }

    @Test
    public final void testContainsFalse() {

        Set<String> s = this.createFromArgsTest("b");

        String x = s.removeAny();
        boolean hasX = true;
        if (x == "a") {
            hasX = false;
        }
        assertEquals(false, hasX);
    }

    @Test
    public final void testsize() {

        Set<String> s = this.createFromArgsTest("a", "b", "c");

        int size = s.size();

        assertEquals(3, size);
    }

    @Test
    public final void testsizeEmpty() {

        Set<String> s = this.createFromArgsTest();

        int size = s.size();

        assertEquals(0, size);
    }
}
