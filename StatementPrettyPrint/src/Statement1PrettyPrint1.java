import components.program.Program;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code prettyPrint} for
 * {@code Statement}.
 */
public final class Statement1PrettyPrint1 extends Statement1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Constructs into the given {@code Statement} the BLOCK statement read from
     * the given input file.
     *
     * @param fileName
     *            the name of the file containing 0 or more statements
     * @param s
     *            the constructed BLOCK statement
     * @replaces s
     * @requires <pre>
     * [fileName is the name of a file containing 0 or more valid BL statements]
     * </pre>
     * @ensures s = [BLOCK statement from file fileName]
     */
    private static void loadStatement(String fileName, Statement s) {
        SimpleReader in = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(in);
        s.parseBlock(tokens);
        in.close();
    }

    /**
     * Prints the given number of spaces to the given output stream.
     *
     * @param out
     *            the output stream
     * @param numSpaces
     *            the number of spaces to print
     * @updates out.content
     * @requires out.is_open and spaces >= 0
     * @ensures out.content = #out.content * [numSpaces spaces]
     */
    private static void printSpaces(SimpleWriter out, int numSpaces) {
        for (int i = 0; i < numSpaces; i++) {
            out.print(' ');
        }
    }

    /**
     * Converts c into the corresponding BL condition.
     *
     * @param c
     *            the Condition to convert
     * @return the BL condition corresponding to c
     * @ensures toStringCondition = [BL condition corresponding to c]
     */
    private static String toStringCondition(Condition c) {
        String result;
        switch (c) {
            case NEXT_IS_EMPTY: {
                result = "next-is-empty";
                break;
            }
            case NEXT_IS_NOT_EMPTY: {
                result = "next-is-not-empty";
                break;
            }
            case NEXT_IS_ENEMY: {
                result = "next-is-enemy";
                break;
            }
            case NEXT_IS_NOT_ENEMY: {
                result = "next-is-not-enemy";
                break;
            }
            case NEXT_IS_FRIEND: {
                result = "next-is-friend";
                break;
            }
            case NEXT_IS_NOT_FRIEND: {
                result = "next-is-not-friend";
                break;
            }
            case NEXT_IS_WALL: {
                result = "next-is-wall";
                break;
            }
            case NEXT_IS_NOT_WALL: {
                result = "next-is-not-wall";
                break;
            }
            case RANDOM: {
                result = "random";
                break;
            }
            case TRUE: {
                result = "true";
                break;
            }
            default: {
                // this will never happen...
                result = "";
                break;
            }
        }
        return result;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Statement1PrettyPrint1() {
        super();
    }

    /*
     * Secondary methods ------------------------------------------------------
     */

    public static void renameInstruction(Statement s, String oldName, String newName) {

        switch (s.kind()) {
            case BLOCK: {
                for (int i = 0; i < s.lengthOfBlock(); i++) {
                    Statement ns = s.removeFromBlock(i);
                    renameInstruction(ns, oldName, newName);
                    s.addToBlock(i, ns);
                }
                break;
            }
            case IF: {
                Statement ns = s.newInstance();
                Condition c = s.disassembleIf(ns);
                renameInstruction(ns, oldName, newName);
                s.assembleIf(c, ns);
                break;
            }
            case IF_ELSE: {
                Statement ns1 = s.newInstance();
                Statement ns2 = s.newInstance();
                Condition c = s.disassembleIfElse(ns1, ns2);
                renameInstruction(ns1, oldName, newName);
                renameInstruction(ns2, oldName, newName);
                s.assembleIfElse(c, ns1, ns2);
                break;
            }
            case WHILE: {
                Statement ns = s.newInstance();
                Condition c = s.disassembleWhile(ns);
                renameInstruction(ns, oldName, newName);
                s.assembleWhile(c, ns);
                break;
            }
            case CALL: {

                String call = s.disassembleCall();
                if (call.equals(oldName)) {
                    call = newName;
                }
                s.assembleCall(call);

                break;
            }
        }
    }

    @Override
    public void prettyPrint(SimpleWriter out, int offset) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";
        assert offset >= 0 : "Violation of: 0 <= offset";

        switch (this.kind()) {
            case BLOCK: {

                System.out.println("kind: " + this.kind());
                System.out.println("block: " + this.kind().BLOCK);
                for (int i = 0; i < this.lengthOfBlock(); i++) {
                    Statement ns = this.removeFromBlock(i);
                    ns.prettyPrint(out, offset);
                    this.addToBlock(i, ns);
                }

                break;
            }
            case IF: {
                Statement ns = this.newInstance();
                Condition c = this.disassembleIf(ns);
                printSpaces(out, offset);
                out.println("IF " + toStringCondition(c) + " THEN");
                ns.prettyPrint(out, offset + Program.INDENT_SIZE);
                printSpaces(out, offset);
                out.println("END IF");
                this.assembleIf(c, ns);

                break;
            }
            case IF_ELSE: {
                Statement ns1 = this.newInstance();
                Statement ns2 = this.newInstance();
                Statement.Condition c = this.disassembleIfElse(ns1, ns2);
                printSpaces(out, offset);
                out.println("IF " + toStringCondition(c) + " THEN");
                ns1.prettyPrint(out, offset + Program.INDENT_SIZE);
                printSpaces(out, offset);
                out.println("ELSE");
                ns2.prettyPrint(out, offset + Program.INDENT_SIZE);
                printSpaces(out, offset);
                out.println("END IF");
                //out.println();
                this.assembleIfElse(c, ns1, ns2);

                break;
            }
            case WHILE: {
                Statement ns = this.newInstance();
                Condition c = this.disassembleWhile(ns);
                printSpaces(out, offset);
                out.println("WHILE " + toStringCondition(c) + " DO");
                ns.prettyPrint(out, offset + Program.INDENT_SIZE);
                printSpaces(out, offset);
                out.println("END WHILE");
                //out.println();
                this.assembleWhile(c, ns);
                break;
            }
            case CALL: {
                // printSpaces(out, offset);
                String call = this.disassembleCall();
                printSpaces(out, offset);
                out.println(call);
                this.assembleCall(call);

                break;
            }
            default: {
                // this will never happen...
                break;
            }
        }
    }

    /*
     * Main test method -------------------------------------------------------
     */

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
         * Get input file name
         */
        out.print("Enter valid BL statement file name: ");
        String fileName = in.nextLine();
        /*
         * Generate expected output in file "data/expected-output.txt"
         */
        out.println("*** Generating expected output ***");
        Statement s1 = new Statement1();
        loadStatement(fileName, s1);
        // renameInstruction(s1, "skip", "cats");
        SimpleWriter ppOut = new SimpleWriter1L("data/expected-output.txt");
        s1.prettyPrint(ppOut, 2);

        ppOut.close();
        /*
         * Generate actual output in file "data/actual-output.txt"
         */
        out.println("*** Generating actual output ***");
        Statement s2 = new Statement1PrettyPrint1();
        loadStatement(fileName, s2);
        ppOut = new SimpleWriter1L("data/actual-output.txt");
        s2.prettyPrint(ppOut, 2);
        ppOut.close();
        /*
         * Check that prettyPrint restored the value of the statement
         */
        if (s2.equals(s1)) {
            out.println("Statement value restored correctly.");
        } else {
            out.println("Error: statement value was not restored.");
        }

        in.close();
        out.close();
    }

}
