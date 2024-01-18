import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Selin Kirbas
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        //create NaturalNumber variable to transfer values to & reduce return statements
        NaturalNumber n = new NaturalNumber2(0);

        //find the number tags to get their attribute value (the numbers)
        if (exp.label().equals("number")) {
            //value is a String and we need to return a NaturalNumber
            n = new NaturalNumber2(exp.attributeValue("value"));
        }

        //use loops to confirm which operators are being used

        //check if addition is used
        if (exp.label().equals("plus")) {
            NaturalNumber sum = new NaturalNumber2(evaluate(exp.child(0)));
            sum.add(evaluate(exp.child(1)));
            n.transferFrom(sum);
        }

        //check if subtraction is used
        if (exp.label().equals("minus")) {
            NaturalNumber diff = new NaturalNumber2(evaluate(exp.child(0)));

            //check if NaturalNumber we are subtracting by is less than the number
            //being subtracted (so subtracting them would result in a negative
            //NaturalNumber)
            if ((evaluate(exp.child(1)).compareTo(diff) > 0)) {
                //if negative, then print error message and terminate application
                //due to violated precondition
                Reporter.fatalErrorToConsole(
                        "Error, cannot have negative NaturalNumber!");
            }
            diff.subtract(evaluate(exp.child(1)));

            n.transferFrom(diff);
        }

        //check if multiplication is used
        if (exp.label().equals("times")) {
            NaturalNumber product = new NaturalNumber2(evaluate(exp.child(0)));
            product.multiply(evaluate(exp.child(1)));
            n.transferFrom(product);
        }

        //check if division is used
        if (exp.label().equals("divide")) {
            NaturalNumber quotient = new NaturalNumber2(evaluate(exp.child(0)));
            NaturalNumber div = new NaturalNumber2(evaluate(exp.child(1)));

            //check if number we are dividing by is zero
            if (div.toInt() == 0) {
                //if zero, then print error message and terminate application
                //due to violated precondition
                Reporter.fatalErrorToConsole("Error, cannot divide by zero!");
            }
            quotient.divide(div);

            n.transferFrom(quotient);
        }
        return n;
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

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}
