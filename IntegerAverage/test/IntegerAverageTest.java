import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Sample JUnit test fixture for SequenceSmooth.
 *
 * @author Mayo Nakajo
 *
 */
public final class IntegerAverageTest {

    /**
     * Test average with j = Integer.MAX_VALUE and k = Integer.MAX_VALUE - 1.
     */
    @Test
    public void test1() {
        int num1 = Integer.MAX_VALUE;
        int num2 = Integer.MAX_VALUE - 1;
        int avgExpected = Integer.MAX_VALUE - 1;
        int avg = IntegerAverage.average(num1, num2);

        assertEquals(avg, avgExpected);
    }

    /**
     * Test average with j = Integer.MIN_VALUE; and k = Integer.MIN_VALUE + 1
     */
    @Test
    public void test2() {
        int num1 = Integer.MIN_VALUE;
        int num2 = Integer.MIN_VALUE + 1;
        int avgExpected = Integer.MIN_VALUE + 1;
        int avg = IntegerAverage.average(num1, num2);

        assertEquals(avg, avgExpected);
    }

    /**
     * Test average with j = Integer.MIN_VALUE; and k = Integer.MIN_VALUE
     */
    @Test
    public void test3() {
        int num1 = Integer.MIN_VALUE;
        int num2 = Integer.MIN_VALUE;
        int avgExpected = Integer.MIN_VALUE;
        int avg = IntegerAverage.average(num1, num2);

        assertEquals(avg, avgExpected);
    }

    /**
     * Test average with j = Integer.MAX_VALUE; and k = Integer.MAX_VALUE
     */
    @Test
    public void test4() {
        int num1 = Integer.MAX_VALUE;
        int num2 = Integer.MAX_VALUE;
        int avgExpected = Integer.MAX_VALUE;
        int avg = IntegerAverage.average(num1, num2);

        assertEquals(avg, avgExpected);
    }

    /**
     * Test average with j = 5; and k = 8
     */
    @Test
    public void test5() {
        int num1 = 5;
        int num2 = 8;
        int avgExpected = 6;
        int avg = IntegerAverage.average(num1, num2);

        assertEquals(avg, avgExpected);
    }

    /**
     * Test average with j = -5; and k = -8
     */
    @Test
    public void test6() {
        int num1 = -5;
        int num2 = -8;
        int avgExpected = -6;
        int avg = IntegerAverage.average(num1, num2);

        assertEquals(avg, avgExpected);
    }

    /**
     * Test average with j = 11; and k = -4
     */
    @Test
    public void test7() {
        int num1 = 11;
        int num2 = -4;
        int avgExpected = 3;
        int avg = IntegerAverage.average(num1, num2);

        assertEquals(avg, avgExpected);
    }

    /**
     * Test average with j = -3; and k = 2
     */
    @Test
    public void test8() {
        int num1 = -3;
        int num2 = 2;
        int avgExpected = 0;
        int avg = IntegerAverage.average(num1, num2);

        assertEquals(avg, avgExpected);
    }

    /**
     * Test average with j = 3; and k = 5
     */
    @Test
    public void test9() {
        int num1 = 3;
        int num2 = 5;
        int avgExpected = 4;
        int avg = IntegerAverage.average(num1, num2);

        assertEquals(avg, avgExpected);
    }

    /**
     * Test average with j = -3; and k = -5
     */
    @Test
    public void test10() {
        int num1 = -3;
        int num2 = -5;
        int avgExpected = -4;
        int avg = IntegerAverage.average(num1, num2);

        assertEquals(avg, avgExpected);
    }

}
