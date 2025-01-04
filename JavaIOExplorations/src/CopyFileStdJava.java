import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Program to copy a text file into another file.
 *
 * @author Put your name here
 *
 */
public final class CopyFileStdJava {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CopyFileStdJava() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments: input-file-name output-file-name
     */
    public static void main(String[] args) throws IOException {
//        SimpleWriter out = new SimpleWriter1L();
//        SimpleReader in = new SimpleReader1L();
//        out.print("Enter file name: ");
//        String fileName = in.nextLine();
//        out.print("Enter desination file name: ");
//        String finalFile = in.nextLine();
//        SimpleReader fileIn = new SimpleReader1L(fileName);
//        SimpleWriter fileOut = new SimpleWriter1L(finalFile);
//        while (!fileIn.atEOS()) {
//            fileOut.println(fileIn.nextLine());
//        }
//        out.close();
//        in.close();
//        fileOut.close();
//        fileIn.close();

        // throws
//        BufferedReader input = new BufferedReader(new FileReader(args[0]));
//        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(args[1])));
//        String s = input.readLine();
//        out.println(s);
//        while (s != null) {
//            s = input.readLine();
//            out.println(s);
//        }
//        input.close();
//        out.close();

// try catch
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader(args[0]));
        } catch (IOException e) {
            System.err.println("Error opening file");
            return;
        }
        PrintWriter out;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(args[1])));
        } catch (IOException e) {
            System.err.println("Error creating file");
            return;
        }
        String s = "";
        try {
            s = input.readLine();

        } catch (IOException e) {
            System.err.println("Error reading file");
            return;
        }
        out.println(s);

        while (s != null) {
            try {
                s = input.readLine();
            } catch (IOException e) {
                System.err.println("Error reading file");
                return;
            }
            out.println(s);
        }

        out.close();
        input.close();

    }

}
