import components.statement.Statement;
import components.statement.StatementKernel.Condition;

/**
 * Utility class with method to count the number of calls to primitive
 * instructions (move, turnleft, turnright, infect, skip) in a given
 * {@code Statement}.
 *
 * @author Mayo Nakajo
 *
 */
public final class CountPrimitiveCalls {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CountPrimitiveCalls() {
    }

    /**
     * Reports the number of calls to primitive instructions (move, turnleft,
     * turnright, infect, skip) in a given {@code Statement}.
     *
     * @param s
     *            the {@code Statement}
     * @return the number of calls to primitive instructions in {@code s}
     * @ensures <pre>
     * countOfPrimitiveCalls =
     *  [number of calls to primitive instructions in s]
     * </pre>
     */
    public static int countOfPrimitiveCalls(Statement s) {
        int count = 0;
        switch (s.kind()) {
            case BLOCK: {
                /*
                 * Add up the number of calls to primitive instructions in each
                 * nested statement in the BLOCK.
                 */
                for (int i = 0; i < s.lengthOfBlock(); i++) {
                    Statement ns = s.removeFromBlock(i);
                    System.out.print("    ");
                    count += countOfPrimitiveCalls(ns);
                    s.addToBlock(i, ns);
                }

                break;
            }
            case IF: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the IF.
                 */

                Statement ns = s.newInstance();
                Condition c = s.disassembleIf(ns);
                System.out.println("IF " + c + " THEN");
                count += countOfPrimitiveCalls(ns);
                System.out.println("END IF");
                s.assembleIf(c, ns);

                break;
            }
            case IF_ELSE: {
                /*
                 * Add up the number of calls to primitive instructions in the
                 * "then" and "else" bodies of the IF_ELSE.
                 */

                Statement ns1 = s.newInstance();
                Statement ns2 = s.newInstance();
                Statement.Condition c = s.disassembleIfElse(ns1, ns2);

                System.out.println("IF " + c + " THEN");
                count += countOfPrimitiveCalls(ns1) + countOfPrimitiveCalls(ns2);
                System.out.println("END IF");
                s.assembleIfElse(c, ns1, ns2);
                break;
            }
            case WHILE: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the WHILE.
                 */

                Statement ns = s.newInstance();
                Condition c = s.disassembleWhile(ns);
                System.out.println("WHILE " + c + " DO");

                count += countOfPrimitiveCalls(ns);
                System.out.println("END WHILE");
                s.assembleWhile(c, ns);

                break;
            }
            case CALL: {
                /*
                 * This is a leaf: the count can only be 1 or 0. Determine
                 * whether this is a call to a primitive instruction or not.
                 */

                String call = s.disassembleCall();
                if (call.equals("move") || call.equals("turnleft")
                        || call.equals("turnright") || call.equals("infect")
                        || call.equals("skip")) {
                    count++;
                }
                System.out.println("    " + call);
                s.assembleCall(call);

                break;
            }
            default: {
                // this will never happen...can you explain why?
                break;
            }
        }
        return count;
    }

}
