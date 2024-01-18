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
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
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
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        //create int variable to reduce return statements
        int n = 0;

        //create divisor variable for divide operator
        int div;

        //find the number tags to get their attribute value (the numbers)
        if (exp.label().equals("number")) {
            //value is a String and we need to return an int
            n = Integer.parseInt(exp.attributeValue("value"));
        }

        //use loops to confirm which operators are being used

        //check if addition is used
        if (exp.label().equals("plus")) {
            n = evaluate(exp.child(0)) + evaluate(exp.child(1));
        }

        //check if subtraction is used
        if (exp.label().equals("minus")) {
            n = evaluate(exp.child(0)) - evaluate(exp.child(1));
        }

        //check if multiplication is used
        if (exp.label().equals("times")) {
            n = evaluate(exp.child(0)) * evaluate(exp.child(1));
        }

        //check if division is used
        if (exp.label().equals("divide")) {
            div = evaluate(exp.child(1));

            //check if number we are dividing by is zero
            if (div == 0) {
                //if zero, then print error message and terminate application
                //due to violated precondition
                Reporter.fatalErrorToConsole("Error, cannot divide by zero!");
            }
            n = evaluate(exp.child(0)) / div;
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
