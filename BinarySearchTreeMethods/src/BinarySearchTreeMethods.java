import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Utility class with implementation of binary search tree static, generic
 * methods isInTree (and removeSmallest).
 *
 * @mathdefinitions <pre>
 * IS_BST(
 *   tree: binary tree of T
 *  ): boolean satisfies
 *  [tree satisfies the binary search tree properties as described in the
 *   slides with the ordering reported by compareTo for T, including that
 *   it has no duplicate labels]
 * </pre>
 *
 * @author Put your name here
 *
 */
public final class BinarySearchTreeMethods {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private BinarySearchTreeMethods() {
    }

    /**
     * Returns whether {@code x} is in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be searched for
     * @return true if t contains x, false otherwise
     * @requires IS_BST(t)
     * @ensures isInTree = (x is in labels(t))
     */
    public static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t, T x) {

        boolean inTree = false;
        BinaryTree<T> left = new BinaryTree1<>();
        BinaryTree<T> right = new BinaryTree1<>();

        if (t.size() > 0) {
            T root = t.disassemble(left, right);
            inTree = root.equals(x) || isInTree(left, x) || isInTree(right, x);
            t.assemble(root, left, right);
        }
        return inTree;
    }

    /**
     * Removes and returns the smallest (left-most) label in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove the label
     * @return the smallest label in the given {@code BinaryTree}
     * @updates t
     * @requires IS_BST(t) and |t| > 0
     * @ensures <pre>
     * IS_BST(t)  and  removeSmallest = [the smallest label in #t]  and
     *  labels(t) = labels(#t) \ {removeSmallest}
     * </pre>
     */
    public static <T> T removeSmallest(BinaryTree<T> t) {

        BinaryTree<T> left = new BinaryTree1<>();
        BinaryTree<T> right = new BinaryTree1<>();
        T small = null;

        if (t.size() > 0) {
            T root = t.disassemble(left, right);
            if (left.size() > 0) {
                small = removeSmallest(left);
                t.assemble(root, left, right);
            } else {
                small = root;
                t.transferFrom(right);
            }

        }
        return small;
    }

    public static <T> T removeBiggest(BinaryTree<T> t) {
        BinaryTree<T> left = new BinaryTree1<>();
        BinaryTree<T> right = new BinaryTree1<>();
        T big = null;
        System.out.println("t: " + t);
        if (t.size() > 0) {

            T root = t.disassemble(left, right);
            if (right.size() > 0) {
                big = removeBiggest(right);
                t.assemble(root, left, right);
            } else {
                big = root;
            }
            t.assemble(root, left, right);
        }

        return big;
    }

    public static <T> void add(BinaryTree<T> t, String x) {

        BinaryTree<T> left = new BinaryTree1<>();
        BinaryTree<T> right = new BinaryTree1<>();
        System.out.println("add: " + t);
        if (t.size() > 0) {
            T root = t.disassemble(left, right);
            if (x.compareTo((String) root) <= 0) {
                add(left, x);
                t.assemble(root, left, right);
            } else {
                add(right, x);
                t.assemble(root, left, right);
            }

        } else {
            t.assemble((T) x, left, right);
        }

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

        /*
         * Input tree labels and construct BST.
         */
        out.println("Input the distinct labels for a binary search tree "
                + "in the order in which you want them inserted.");
        out.println("Press Enter on an empty line to terminate your input.");
        out.println();
        out.print("Next label: ");
        String str = in.nextLine();
        BinaryTree<String> t = new BinaryTree1<String>();
        while (str.length() > 0) {
            BinaryTreeUtility.insertInTree(t, str);
            out.println();
            out.println("t = " + BinaryTreeUtility.treeToString(t));
            out.println();
            out.print("Next label: ");
            str = in.nextLine();
        }
        /*
         * Input strings and check whether each is in the BST or not.
         */
        out.println();
        out.print("  Input a label to search "
                + "(or just press Enter to input a new tree): ");
        String label = in.nextLine();
        while (label.length() > 0) {
            if (isInTree(t, label)) {
                out.println("    \"" + label + "\" is in the tree");
            } else {
                out.println("    \"" + label + "\" is not in the tree");
            }
            out.print("  Input a label to search "
                    + "(or just press Enter to terminate the program): ");
            label = in.nextLine();
        }
        /*
         * Output BST labels in order.
         */

//        out.println();
//        out.println("Labels in BST in order:");
//        while (t.size() > 0) {
//            label = removeSmallest(t);
//            out.println("  " + label);
//        }

        out.println(removeBiggest(t));
        out.println(t);
        add(t, "2");
        out.println(t);

        in.close();
        out.close();
    }
}
