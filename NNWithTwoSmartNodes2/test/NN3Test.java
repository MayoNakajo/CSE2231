import components.list.List;
import components.list.List1L;
import components.naturalnumber.NaturalNumber1L;

/**
 * Customized JUnit test fixture for {@code List2}.
 */
public class NN3Test extends NaturalNumber1LTest {

    @Override
    protected final NN3 constructorTest() {
        return new NN3();
    }

    @Override
    protected final NaturalNumber1L constructorRef() {
        return new NaturalNumber1L();
    }

}
