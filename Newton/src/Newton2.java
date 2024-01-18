import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program uses Newton iteration to compute estimate of square root of a number
 * given by the user to within relative error of 0.01%.
 *
 * @author Selin Kirbas
 *
 */
public final class Newton2 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Newton2() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            zero or a positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x) {
        double r = 0.0;
        final double ep = 0.0001;
        r = x;

        if (x == 0) {
            r = 0;
        }

        while (Math.abs(r - x / r) > ep * ep) {
            r = (r + (x / r)) / 2.0;
        }
        return r;
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

        String response = "y";

        while (response.equals("y")) {
            out.println("Please enter a number:");
            double x = in.nextDouble();

            double ans = sqrt(x);

            out.println(
                    "The square root of the given number " + x + " is " + ans);
            out.println("Do you wish to calculate another square root?");
            response = in.nextLine();
        }

        out.println("End.");
        /*
         * Close input and output streams
         */
        in.close();
        out.close();

    }

}
