import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Reports the values of the exponents that bring the de Jager formula
 * w^a*x^b*y^c*z^d to μ within 1% error.
 *
 * @author Selin Kirbas
 *
 */
public final class ABCDGuesser2 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ABCDGuesser2() {
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        double mu = 0.0;
        boolean flag = true;

        while (flag) {
            out.println("Please enter a positive, real number for μ.");
            String response = in.nextLine();

            if (FormatChecker.canParseDouble(response)
                    && Double.parseDouble(response) > 0.0) {
                mu = Double.parseDouble(response);
                flag = false;
            } else {
                out.println("Invalid response.");
            }
        }
        return mu;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        double num = 0.0;
        boolean flag = true;

        while (flag) {
            out.println(
                    "Please enter a positive, real number that is not equal to 1.");
            String response = in.nextLine();

            if (FormatChecker.canParseDouble(response)
                    && Double.parseDouble(response) != 1.0
                    && Double.parseDouble(response) > 0.0) {
                num = Double.parseDouble(response);
                flag = false;

            } else {
                out.println("Invalid response.");
            }

        }
        return num;

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

        final int n100 = 100;

        out.println("Value for μ:");
        double mu = getPositiveDouble(in, out);

        out.println("Value for w:");
        double w = getPositiveDoubleNotOne(in, out);

        out.println("Value for x:");
        double x = getPositiveDoubleNotOne(in, out);

        out.println("Value for y:");
        double y = getPositiveDoubleNotOne(in, out);

        out.println("Value for z:");
        double z = getPositiveDoubleNotOne(in, out);

        double expA = 0.0;
        double expB = 0.0;
        double expC = 0.0;
        double expD = 0.0;

        final double[] charm = { -5, -4, -3, -2, -1, (double) -1 / 2,
                (double) -1 / 3, (double) -1 / 4, 0, (double) 1 / 4,
                (double) 1 / 3, (double) 1 / 2, 1, 2, 3, 4, 5 };

        double num = 0.0;

        for (int countA = 0; countA < charm.length; countA++) {
            double num1 = (Math.pow(w, charm[countA]));

            for (int countB = 0; countB < charm.length; countB++) {
                double num2 = (Math.pow(x, charm[countB]));

                for (int countC = 0; countC < charm.length; countC++) {
                    double num3 = (Math.pow(y, charm[countC]));

                    for (int countD = 0; countD < charm.length; countD++) {
                        double num4 = (Math.pow(z, charm[countD]));

                        double result = num1 * num2 * num3 * num4;

                        if (Math.abs(mu - result) < Math.abs(mu - num)) {
                            num = result;
                            expA = charm[countA];
                            expB = charm[countB];
                            expC = charm[countC];
                            expD = charm[countD];
                        }
                    }
                }
            }

        }

        double relError = (Math.abs(mu - num) / mu) * n100;
        out.println("Exponent a is " + expA + ". Exponent b is " + expB
                + ". Exponent c is " + expC + ". Exponent d is " + expD);
        out.println("The value of the de Jager formula is " + num);
        out.println(
                "The relative error of the approximation is " + relError + "%");
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
