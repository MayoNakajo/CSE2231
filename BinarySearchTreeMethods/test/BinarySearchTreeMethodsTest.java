import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;

/**
 * JUnit test fixture for {@code BinarySearchTreeMethods}'s static methods
 * isInTree (and removeSmallest).
 */
public final class BinarySearchTreeMethodsTest {

    /**
     * Constructs and return a BST created by inserting the given {@code args}
     * into an empty tree in the order in which they are provided.
     *
     * @param args
     *            the {@code String}s to be inserted in the tree
     * @return the BST with the given {@code String}s
     * @requires [the Strings in args are all distinct]
     * @ensures createBSTFromArgs = [the BST with the given Strings]
     */
    private static BinaryTree<String> createBSTFromArgs(String... args) {
        BinaryTree<String> t = new BinaryTree1<String>();
        for (String s : args) {
            BinaryTreeUtility.insertInTree(t, s);
        }
        return t;
    }

    /**
     * Test for isInTree returning true.
     */
    @Test
    public void sampleTest() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("b", "a", "c");
        BinaryTree<String> t2 = createBSTFromArgs("b", "a", "c");
        /*
         * Call method under test
         */
        boolean inTree = BinarySearchTreeMethods.isInTree(t1, "a");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, inTree);
        assertEquals(t2, t1);
    }

    /**
     * Test for isInTree returning true.
     */
    @Test
    public void isInTreeTrue() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("b", "a", "c");
        BinaryTree<String> t2 = createBSTFromArgs("b", "a", "c");
        /*
         * Call method under test
         */
        boolean inTree = BinarySearchTreeMethods.isInTree(t1, "a");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, inTree);
        assertEquals(t2, t1);
    }

    /**
     * Test for isInTree returning false.
     */
    @Test
    public void isInTreeTestFalse() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("b", "a", "c");
        BinaryTree<String> t2 = createBSTFromArgs("b", "a", "c");
        /*
         * Call method under test
         */
        boolean inTree = BinarySearchTreeMethods.isInTree(t1, "d");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, inTree);
        assertEquals(t2, t1);
    }

    /**
     * Test for isInTree returning true.
     */
    @Test
    public void isInTreeTestTrue() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("b", "a");
        BinaryTree<String> t2 = createBSTFromArgs("b", "a");
        /*
         * Call method under test
         */
        boolean inTree = BinarySearchTreeMethods.isInTree(t1, "b");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, inTree);
        assertEquals(t2, t1);
    }

    /**
     * Test for removeSmallest removing "a".
     */
    @Test
    public void removeSmallestTest1() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("b", "a", "c");
        BinaryTree<String> t2 = createBSTFromArgs("b", "c");
        /*
         * Call method under test
         */
        String sm = BinarySearchTreeMethods.removeSmallest(t1);
        String smExp = "a";
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(smExp, sm);
        assertEquals(t2, t1);
    }

    /**
     * Test for removeSmallest removing "a".
     */
    @Test
    public void removeSmallestTest2() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("b", "d", "c", "a");
        BinaryTree<String> t2 = createBSTFromArgs("b", "d", "c");
        /*
         * Call method under test
         */
        String sm = BinarySearchTreeMethods.removeSmallest(t1);
        String smExp = "a";
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(smExp, sm);
        assertEquals(t2, t1);
    }

    /**
     * Test for removeSmallest removing "b".
     */
    @Test
    public void removeSmallestTest3() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("b", "d", "c", "h", "i", "j");
        BinaryTree<String> t2 = createBSTFromArgs("d", "c", "h", "i", "j");
        /*
         * Call method under test
         */
        String sm = BinarySearchTreeMethods.removeSmallest(t1);
        String smExp = "b";
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(smExp, sm);
        assertEquals(t2, t1);
    }
}
