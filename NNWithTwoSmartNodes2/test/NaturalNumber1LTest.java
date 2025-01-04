import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.list.List;
import components.naturalnumber.NaturalNumber1L;

/**
 * JUnit test fixture for {@code List<String>}'s constructor and kernel methods,
 * plus moveToFinish secondary method.
 *
 * @author Paolo Bucci
 *
 */
public abstract class NaturalNumber1LTest {

    /*
     * Test cases for constructor, addRightFront, removeRightFront, advance,
     * moveToStart, leftLength, and rightLength.
     */

    @Test
    public final void testConstructor() {
        /*
         * Set up variables and call method under test
         */
        NN3 list1 = this.constructorTest();
        NaturalNumber1L list2 = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(list2, list1);
    }

    protected abstract NN3 constructorTest();

    protected abstract NaturalNumber1L constructorRef();

    @Test
    public final void multiplyBy10Test1() {
        /*
         * Set up variables
         */
        NN3 n3 = new NN3(123);
        NaturalNumber1L n = new NaturalNumber1L(123);
        NN3 n1 = (NN3) n;
        /*
         * Call method under test
         */
        
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n3, n);
    }

}
