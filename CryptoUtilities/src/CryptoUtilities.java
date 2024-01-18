import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.random.Random;
import components.random.Random1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Utilities that could be used with RSA cryptosystems.
 *
 * @author Selin Kirbas
 *
 */
public final class CryptoUtilities {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CryptoUtilities() {
    }

    /**
     * Useful constant, not a magic number: 3.
     */
    private static final int THREE = 3;

    /**
     * Pseudo-random number generator.
     */
    private static final Random GENERATOR = new Random1L();

    /**
     * Returns a random number uniformly distributed in the interval [0, n].
     *
     * @param n
     *            top end of interval
     * @return random number in interval
     * @requires n > 0
     * @ensures <pre>
     * randomNumber = [a random number uniformly distributed in [0, n]]
     * </pre>
     */
    public static NaturalNumber randomNumber(NaturalNumber n) {
        assert !n.isZero() : "Violation of: n > 0";
        final int base = 10;
        NaturalNumber result;
        int d = n.divideBy10();
        if (n.isZero()) {
            /*
             * Incoming n has only one digit and it is d, so generate a random
             * number uniformly distributed in [0, d]
             */
            int x = (int) ((d + 1) * GENERATOR.nextDouble());
            result = new NaturalNumber2(x);
            n.multiplyBy10(d);
        } else {
            /*
             * Incoming n has more than one digit, so generate a random number
             * (NaturalNumber) uniformly distributed in [0, n], and another
             * (int) uniformly distributed in [0, 9] (i.e., a random digit)
             */
            result = randomNumber(n);
            int lastDigit = (int) (base * GENERATOR.nextDouble());
            result.multiplyBy10(lastDigit);
            n.multiplyBy10(d);
            if (result.compareTo(n) > 0) {
                /*
                 * In this case, we need to try again because generated number
                 * is greater than n; the recursive call's argument is not
                 * "smaller" than the incoming value of n, but this recursive
                 * call has no more than a 90% chance of being made (and for
                 * large n, far less than that), so the probability of
                 * termination is 1
                 */
                result = randomNumber(n);
            }
        }
        return result;
    }

    /**
     * Finds the greatest common divisor of n and m.
     *
     * @param n
     *            one number
     * @param m
     *            the other number
     * @updates n
     * @clears m
     * @ensures n = [greatest common divisor of #n and #m]
     */
    public static void reduceToGCD(NaturalNumber n, NaturalNumber m) {
        if (!m.isZero()) {
            //get n mod m value
            NaturalNumber mod = new NaturalNumber2(n.divide(m));

            //get n mod m value from r for method call
            n.copyFrom(mod);

            //GCD of n & m = GCD(m, n mod m)
            reduceToGCD(m, n);

            //fulfill conditions in contract
            //update n & ensure n is equal to GCD of #n and #m & clear m
            n.transferFrom(m);
        }
    }

    /**
     * Reports whether n is even.
     *
     * @param n
     *            the number to be checked
     * @return true iff n is even
     * @ensures isEven = (n mod 2 = 0)
     */
    public static boolean isEven(NaturalNumber n) {
        //set up boolean return variable
        boolean result = false;

        //create variable to divide n by
        NaturalNumber two = new NaturalNumber2(2);

        //create a variable to store n value so n isn't altered after divide method call
        NaturalNumber temp = new NaturalNumber2(n);

        //check if remainder of n divided by 2 is equal to 0
        if (temp.divide(two).isZero()) {
            result = true;
        }

        return result;
    }

    /**
     * Updates n to its p-th power modulo m.
     *
     * @param n
     *            number to be raised to a power
     * @param p
     *            the power
     * @param m
     *            the modulus
     * @updates n
     * @requires m > 1
     * @ensures n = #n ^ (p) mod m
     */
    public static void powerMod(NaturalNumber n, NaturalNumber p,
            NaturalNumber m) {
        assert m.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: m > 1";

        //variable to copy if p is zero since anything to 0 is 1
        NaturalNumber one = new NaturalNumber2(1);

        NaturalNumber pTemp = new NaturalNumber2(p);
        NaturalNumber two = new NaturalNumber2(2);

        if (p.isZero()) {
            n.transferFrom(one); //x^0 = 1
        } else if (isEven(p)) { //check if p is even
            //if even, use (n^p/2 mod m)^2 mod m
            pTemp.divide(two);

            powerMod(n, pTemp, m);

            //store n value to square
            NaturalNumber nTemp = new NaturalNumber2(n);

            //square n^p/2 mod m
            n.multiply(nTemp);

            NaturalNumber mod = n.divide(m);

            n.copyFrom(mod);
        } else { //use ((n^p-1/2 mod m)^2 * ([initial]n mod m)) mod m
            //calculate p - 1/2 for method call
            pTemp.decrement();
            pTemp.divide(two);

            //create variable to store initial n value
            NaturalNumber nTemp1 = new NaturalNumber2(n);

            powerMod(n, pTemp, m);

            //store n value after calling method (aka n^p-1/2 mod m)
            NaturalNumber nTemp2 = new NaturalNumber2(n);

            //square n^p-1/2 mod m
            n.multiply(nTemp2);

            n.copyFrom(n.divide(m));

            //create new variable for initial n value mod m
            NaturalNumber mod = new NaturalNumber2(nTemp1.divide(m));

            //(n^p-1/2 mod m)^2 * ([initial]n mod m)
            mod.multiply(n);

            //previous equation mod m
            n.copyFrom(mod.divide(m));
        }
    }

    /**
     * Reports whether w is a "witness" that n is composite, in the sense that
     * either it is a square root of 1 (mod n), or it fails to satisfy the
     * criterion for primality from Fermat's theorem.
     *
     * @param w
     *            witness candidate
     * @param n
     *            number being checked
     * @return true iff w is a "witness" that n is composite
     * @requires n > 2 and 1 < w < n - 1
     * @ensures <pre>
     * isWitnessToCompositeness =
     *     (w ^ 2 mod n = 1)  or  (w ^ (n-1) mod n /= 1)
     * </pre>
     */
    public static boolean isWitnessToCompositeness(NaturalNumber w,
            NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(2)) > 0 : "Violation of: n > 2";
        assert (new NaturalNumber2(1)).compareTo(w) < 0 : "Violation of: 1 < w";
        n.decrement();
        assert w.compareTo(n) < 0 : "Violation of: w < n - 1";
        n.increment();

        //set up boolean return variable
        boolean result = false;

        //create variable to square with powerMod method
        NaturalNumber two = new NaturalNumber2(2);

        //create variable to use for comparison to ensure condition
        //w is a "witness" if it is equal to one (after using powerMod method)
        NaturalNumber one = new NaturalNumber2(1);

        //create variable to store value of w
        NaturalNumber wTemp = new NaturalNumber2(w);

        //call powerMod method to calculate w^2 mod n
        powerMod(w, two, n);

        //check if w^2 mod n is equal to 1
        if (w.compareTo(one) == 0) {
            result = true; //if equal, then w is a witness
        }

        //check next condition

        //create variable to store value of n to prevent n value from changing
        NaturalNumber nTemp = new NaturalNumber2(n);

        //use tempN variable to create n-1 for condition
        nTemp.decrement();

        //call powerMod method to calculate w^(n-1) mod n
        powerMod(w, nTemp, n);

        //check if w^(n-1) mod n is not equal to 1
        if (w.compareTo(one) != 0) {
            result = true; //if equal, then w is a witness
        }

        //transfer original value back to w
        w.transferFrom(wTemp);

        return result;
    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     *
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires n > 1
     * @ensures <pre>
     * isPrime1 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     * </pre>
     */
    public static boolean isPrime1(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";
        boolean isPrime;
        if (n.compareTo(new NaturalNumber2(THREE)) <= 0) {
            /*
             * 2 and 3 are primes
             */
            isPrime = true;
        } else if (isEven(n)) {
            /*
             * evens are composite
             */
            isPrime = false;
        } else {
            /*
             * odd n >= 5: simply check whether 2 is a witness that n is
             * composite (which works surprisingly well :-)
             */
            isPrime = !isWitnessToCompositeness(new NaturalNumber2(2), n);
        }
        return isPrime;
    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     *
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires n > 1
     * @ensures <pre>
     * isPrime2 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     * </pre>
     */
    public static boolean isPrime2(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";

        //set up boolean return variable
        boolean result = true;

        final int fifty = 50;

        //variable for comparison
        NaturalNumber one = new NaturalNumber2(1);

        NaturalNumber temp = new NaturalNumber2(n);

        //set up n-1 variable
        temp.decrement();

        //create loop to generate 50 witness candidates
        for (int i = 0; i < fifty; i++) {
            //generate random numbers in the interval
            NaturalNumber random = randomNumber(temp);

            /*
             * Due to the preconditions of isWitnessToCompositeness, we need to
             * make sure that random (or w) is NOT less than or equal to 1 since
             * it needs to be more than 1 (1 < w). We also need to check if
             * random/w is more than or equal to n - 1 (temp) since the
             * condition states w < n - 1. If random/w will violate these
             * conditions, then we keep looping until random is a number that
             * satisfies the conditions before we call the method.
             */
            while (random.compareTo(one) <= 0 || random.compareTo(temp) >= 0) {
                random = randomNumber(n);
            }

            //check if any of the 50 generated candidates witness n being composite
            if (isWitnessToCompositeness(random, n)) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Generates a likely prime number at least as large as some given number.
     *
     * @param n
     *            minimum value of likely prime
     * @updates n
     * @requires n > 1
     * @ensures n >= #n and [n is very likely a prime number]
     */
    public static void generateNextLikelyPrime(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";

        //set up variable for while loop
        boolean result = false;
        while (!result) {
            //check if n is even since even numbers can't be prime (divisible by 2)
            if (isEven(n)) {
                n.increment(); //increment once to make number odd
            } else {
                //check numbers starting at n and increase through odd numbers
                //(increment twice to make sure number is odd)
                n.increment();
                n.increment();
            }
            //check if isPrime2 is true to break loop
            if (isPrime2(n)) {
                result = true;
            }

        }
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
         * Sanity check of randomNumber method -- just so everyone can see how
         * it might be "tested"
         */
        final int testValue = 17;
        final int testSamples = 100000;
        NaturalNumber test = new NaturalNumber2(testValue);
        int[] count = new int[testValue + 1];
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
        for (int i = 0; i < testSamples; i++) {
            NaturalNumber rn = randomNumber(test);
            assert rn.compareTo(test) <= 0 : "Help!";
            count[rn.toInt()]++;
        }
        for (int i = 0; i < count.length; i++) {
            out.println("count[" + i + "] = " + count[i]);
        }
        out.println("  expected value = "
                + (double) testSamples / (double) (testValue + 1));

        /*
         * Check user-supplied numbers for primality, and if a number is not
         * prime, find the next likely prime after it
         */
        while (true) {
            out.print("n = ");
            NaturalNumber n = new NaturalNumber2(in.nextLine());
            if (n.compareTo(new NaturalNumber2(2)) < 0) {
                out.println("Bye!");
                break;
            } else {
                if (isPrime1(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime1.");
                } else {
                    out.println(n + " is a composite number"
                            + " according to isPrime1.");
                }
                if (isPrime2(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime2.");
                } else {
                    out.println(n + " is a composite number"
                            + " according to isPrime2.");
                    generateNextLikelyPrime(n);
                    out.println("  next likely prime is " + n);
                }
            }
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
