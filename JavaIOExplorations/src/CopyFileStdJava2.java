import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Program to copy a text file into another file.
 *
 * @author Put your name here
 *
 */
public final class CopyFileStdJava2 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CopyFileStdJava2() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments: input-file-name output-file-name
     */
    public static void main(String[] args) throws IOException {

        List<String> lines;

        if (args.length < 2) {
            System.err.println("Error:  requires both input and output file names");
            return;
        } else {
            lines = new ArrayList<>();
            try (BufferedReader in = new BufferedReader(new FileReader(args[0]))) {
                String line = in.readLine();
                while (line != null) {
                    lines.add(line);
                    line = in.readLine();
                }
            } catch (FileNotFoundException e) {
                System.err.println("Error: cannot open input file");
                return;
            } catch (IOException e) {
                System.err.println("Error: cannot read from input file");
            }

            try (PrintWriter out = new PrintWriter(
                    new BufferedWriter(new FileWriter(args[1])))) {
                for (String s : lines) {
                    out.println(s);
                }
            } catch (IOException e) {
                System.err.println("Error: cannot open output file");
                return;
            }
        }

        /**
         * try { String line = input.readLine(); while(line != null){
         * output.println(line); line = input.readLine(); } } catch (IOException
         * e){ System.err.println("Error reading from input file"); }
         */

    }

}
