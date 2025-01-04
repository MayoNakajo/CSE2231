import components.map.Map;
import components.map.Map.Pair;
import components.program.Program;
import components.program.Program1;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;

/**
 * Layered implementation of secondary method {@code prettyPrint} for
 * {@code Program}.
 */
public final class Program1PrettyPrint1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Constructs into the given {@code Program} the program read from the given
     * input file.
     *
     * @param fileName
     *            the name of the file containing the program
     * @param p
     *            the constructed program
     * @replaces p
     * @requires [fileName is the name of a file containing a valid BL program]
     * @ensures p = [program from file fileName]
     */
    private static void loadProgram(String fileName, Program p) {
        SimpleReader in = new SimpleReader1L(fileName);
        p.parse(in);
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

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1PrettyPrint1() {
        super();
    }

    /*
     * Secondary methods ------------------------------------------------------
     */

    public static void renameInstruction(Program p, String oldName, String newName) {
        Map<String, Statement> context = p.newContext();
        Map<String, Statement> cntxt = p.newContext();

        p.swapContext(context);
        while (context.size() > 0) {
            Pair<String, Statement> pair = context.removeAny();
            if (pair.key().equals(oldName)) {
                cntxt.add(newName, pair.value());
            } else {
                cntxt.add(pair.key(), pair.value());
            }

        }
        p.swapContext(cntxt);

    }

    @Override
    public void prettyPrint(SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("PROGRAM " + this.name() + " IS");
        Map<String, Statement> ctx = this.newContext(); // instructions
        this.swapContext(ctx);
        for (Pair<String, Statement> p : ctx) {
            out.println();
            printSpaces(out, INDENT_SIZE);
            out.println("INSTRUCTION " + p.key() + " IS");
            p.value().prettyPrint(out, INDENT_SIZE * 2);
            printSpaces(out, INDENT_SIZE);
            out.println("END " + p.key());
            out.println();
        }
        this.swapContext(ctx);
        out.println("BEGIN");
        Statement st = this.newBody();

        this.swapBody(st);
        st.prettyPrint(out, INDENT_SIZE);

        out.println("END " + this.name());
        this.swapBody(st);
    }

    public static void simplifyIfElse(Program p) {
        Map<String, Statement> context = p.newContext();
        Map<String, Statement> cntxt = p.newContext();// instructions
        p.swapContext(context);
        while (context.size() > 0) {
            Pair<String, Statement> pair = context.removeAny();
            simplifyIfElse(pair.value());
            cntxt.add(pair.key(), pair.value());
        }
        p.swapContext(cntxt);

        Statement body = p.newBody();
        p.swapBody(body);
        simplifyIfElse(body);
        p.swapBody(body);

    }

    public static void simplifyIfElse(Statement s) {

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
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Generate expected output in file "data/expected-output.txt"
         */
        out.println("*** Generating expected output ***");
        Program p1 = new Program1();
        loadProgram(fileName, p1);

        renameInstruction(p1, "FindObstacle", "FindPhone");
        SimpleWriter ppOut = new SimpleWriter1L("data/expected-output.txt");
        p1.prettyPrint(ppOut);
        ppOut.close();
        /*
         * Generate actual output in file "data/actual-output.txt"
         */
        out.println("*** Generating actual output ***");
        Program p2 = new Program1PrettyPrint1();
        loadProgram(fileName, p2);
        ppOut = new SimpleWriter1L("data/actual-output.txt");
        p2.prettyPrint(ppOut);
        ppOut.close();
        /*
         * Check that prettyPrint restored the value of the program
         */
        if (p2.equals(p1)) {
            out.println("Program value restored correctly.");
        } else {
            out.println("Error: program value was not restored.");
        }

        in.close();
        out.close();
    }

}
