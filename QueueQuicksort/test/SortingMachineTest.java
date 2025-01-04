import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    // TODO - add test cases for add, changeToExtractionMode, removeFirst,
    // isInInsertionMode, order, and size

    @Test
    public final void testAdd1() {
        SortingMachine<String> sm = this.createFromArgsTest(ORDER, true, "a");
        SortingMachine<String> smExpected = this.createFromArgsTest(ORDER, true, "a",
                "b");
        sm.add("b");
        assertEquals(smExpected, sm);
    }

    @Test
    public final void testChangeToExtractionMode() {
        SortingMachine<String> sm = this.createFromArgsTest(ORDER, true, "a");
        SortingMachine<String> smExpected = this.createFromArgsTest(ORDER, false, "a");
        sm.changeToExtractionMode();
        assertEquals(smExpected, sm);
    }

    @Test
    public final void testRemoveFirstOne() {
        SortingMachine<String> sm = this.createFromArgsTest(ORDER, true, "a");
        SortingMachine<String> smExpected = this.createFromArgsTest(ORDER, true);
        sm.changeToExtractionMode();
        sm.removeFirst();
        assertEquals(smExpected, sm);
    }

    @Test
    public final void testRemoveFirstTwo() {
        SortingMachine<String> sm = this.createFromArgsTest(ORDER, true, "a", "b");
        SortingMachine<String> smExpected = this.createFromArgsTest(ORDER, true, "a");
        sm.changeToExtractionMode();
        sm.removeFirst();
        assertEquals(smExpected, sm);
    }

    @Test
    public final void testIsInInsertionMode() {
        SortingMachine<String> sm = this.createFromArgsTest(ORDER, true, "a");
        SortingMachine<String> smExpected = this.createFromArgsTest(ORDER, true, "a");
        assertEquals(true, sm.isInInsertionMode());
        smExpected.isInInsertionMode();
        assertEquals(smExpected, sm);
    }

    @Test
    public final void testOrder() {
        SortingMachine<String> sm = this.createFromArgsTest(ORDER, true, "a");
        Comparator<String> sort = sm.order();
        Comparator<String> sortExpected = new StringLT();
        assertEquals(sortExpected, sort);
    }

    @Test
    public final void testSizeEmpty() {
        SortingMachine<String> sm = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> smExpected = this.createFromArgsTest(ORDER, true);
        int size = sm.size();
        assertEquals(0, size);
        assertEquals(smExpected, sm);
    }

    @Test
    public final void testSizeOne() {
        SortingMachine<String> sm = this.createFromArgsTest(ORDER, true, "a");
        SortingMachine<String> smExpected = this.createFromArgsTest(ORDER, true, "a");
        int size = sm.size();
        assertEquals(1, size);
        assertEquals(smExpected, sm);
    }

    @Test
    public final void testSizeMultiple() {
        SortingMachine<String> sm = this.createFromArgsTest(ORDER, true, "a", "b", "c",
                "d", "e");
        SortingMachine<String> smExpected = this.createFromArgsTest(ORDER, true, "a", "b",
                "c", "d", "e");
        int size = sm.size();
        assertEquals(5, size);
        assertEquals(smExpected, sm);
    }
}
