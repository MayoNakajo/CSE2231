
import components.map.Map;
import components.map.Map1L;

/**
 * Implementation of {@code EmailAccount}.
 *
 * @author Mayo Nakajo
 *
 */
public final class EmailAccount1 implements EmailAccount {

    /*
     * Private members --------------------------------------------------------
     */

    private String firstN;
    private String lastN;
    private String email;
    static Map<String, Integer> map = new Map1L<>();

    /*
     * Constructor ------------------------------------------------------------
     */

    /**
     * Constructor.
     *
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     */
    public EmailAccount1(String firstName, String lastName) {

        this.firstN = firstName;
        this.lastN = lastName;
//        if (this.map.hasKey(lastName.toLowerCase())) {
//            int current = this.map.value(lastName.toLowerCase());
//            int newNum = current++;
//            this.map.replaceValue(lastName.toLowerCase(), newNum);
//            this.email = lastName.toLowerCase() + newNum + "@osu.edu";
//
//        } else {
//            this.map.add(lastName.toLowerCase(), 1);
//            this.email = lastName.toLowerCase() + ".1@osu.edu";
//        }
        String result = "";
        int num = 1;
        if(EmailAccount1.map.hasKey(this.lastN)) {
            num = EmailAccount1.map.value(this.lastN);
            num++;
            EmailAccount1.map.replaceValue(this.lastN, num);
        } else {
            EmailAccount1.map.add(this.lastN, 1);
        }

        result = this.lastN;
        result = result.toLowerCase();
        result =

    }

    /*
     * Methods ----------------------------------------------------------------
     */

    @Override
    public String name() {

        return this.firstN + " " + this.lastN;

    }

    @Override
    public String emailAddress() {

        return this.email;
    }

    @Override
    public String toString() {

        return "Name : " + this.firstN + " " + this.lastN + ", " + "Email : "
                + this.emailAddress();
    }

}
