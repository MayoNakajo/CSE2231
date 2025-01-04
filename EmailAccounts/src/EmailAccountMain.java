import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Simple program to exercise EmailAccount functionality.
 */
public final class EmailAccountMain {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private EmailAccountMain() {
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
         * Should print: Brutus Buckeye
         */
        Sequence<String> seq = new Sequence1L<>();
        out.print("Enter full name: ");
        String fullName = in.nextLine();
        while (!fullName.equals("")) {
            int idx = 0;
            for (int i = 0; i < fullName.length(); i++) {
                if (fullName.charAt(i) != ' ') {
                    idx = i;
                }
            }
            String first = fullName.substring(0, idx);
            String last = fullName.substring(idx);

            EmailAccount myAccount = new EmailAccount1(first, last);
            seq.add(0, myAccount.emailAddress());
            for (String email : seq) {
                out.println(email);
            }
            out.print("Enter full name: ");
            fullName = in.nextLine();
        }
        /*
         * Should print: buckeye.1@osu.edu
         */
        // out.println(myAccount.emailAddress());
        /*
         * Should print: Name: Brutus Buckeye, Email: buckeye.1@osu.edu
         */
        // out.println(myAccount);
        in.close();
        out.close();
    }

}
