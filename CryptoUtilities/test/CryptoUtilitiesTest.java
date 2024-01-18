import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * @author Selin Kirbas
 *
 */
public class CryptoUtilitiesTest {
    /**
     * Tests of reduceToGCD.
     */
    @Test
    public void testReduceToGCD_0_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(0);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    @Test
    public void testReduceToGCD_30_21() {
        NaturalNumber n = new NaturalNumber2(30);
        NaturalNumber nExpected = new NaturalNumber2(3);
        NaturalNumber m = new NaturalNumber2(21);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    @Test
    public void testReduceToGCD_66_12() {
        NaturalNumber n = new NaturalNumber2(66);
        NaturalNumber nExpected = new NaturalNumber2(6);
        NaturalNumber m = new NaturalNumber2(12);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /**
     * Tests of isEven.
     */
    @Test
    public void testIsEven_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    @Test
    public void testIsEven_1() {
        NaturalNumber n = new NaturalNumber2(1);
        NaturalNumber nExpected = new NaturalNumber2(1);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    @Test
    public void testIsEven_62() {
        NaturalNumber n = new NaturalNumber2(62);
        NaturalNumber nExpected = new NaturalNumber2(62);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    @Test
    public void testIsEven_57() {
        NaturalNumber n = new NaturalNumber2(57);
        NaturalNumber nExpected = new NaturalNumber2(57);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    /**
     * Tests of powerMod.
     */
    @Test
    public void testPowerMod_0_0_2() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(0);
        NaturalNumber pExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(2);
        NaturalNumber mExpected = new NaturalNumber2(2);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    @Test
    public void testPowerMod_17_18_19() {
        NaturalNumber n = new NaturalNumber2(17);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(18);
        NaturalNumber pExpected = new NaturalNumber2(18);
        NaturalNumber m = new NaturalNumber2(19);
        NaturalNumber mExpected = new NaturalNumber2(19);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    @Test
    public void testPowerMod_3_9_6() {
        NaturalNumber n = new NaturalNumber2(3);
        NaturalNumber nExpected = new NaturalNumber2(3);
        NaturalNumber p = new NaturalNumber2(9);
        NaturalNumber pExpected = new NaturalNumber2(9);
        NaturalNumber m = new NaturalNumber2(6);
        NaturalNumber mExpected = new NaturalNumber2(6);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    /**
     * Tests of isWitnessToCompositeness.
     */

    @Test
    public void testIsWitnessToCompositeness_2_5() {
        NaturalNumber w = new NaturalNumber2(2);
        NaturalNumber wExpected = new NaturalNumber2(2);
        NaturalNumber n = new NaturalNumber2(5);
        NaturalNumber nExpected = new NaturalNumber2(5);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals(wExpected, w);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    /**
     * Tests of isPrime2.
     */

    @Test
    public void testIsPrime2_35() {
        NaturalNumber n = new NaturalNumber2(35);
        NaturalNumber nExpected = new NaturalNumber2(35);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    /**
     * Tests of generateNextLikelyPrime.
     */

    @Test
    public void testGenerateNextLikelyPrime_7_11() {
        NaturalNumber n = new NaturalNumber2(7);
        NaturalNumber nExpected = new NaturalNumber2(11);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(nExpected, n);
    }

    @Test
    public void testGenerateNextLikelyPrime_12_13() {
        NaturalNumber n = new NaturalNumber2(12);
        NaturalNumber nExpected = new NaturalNumber2(13);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(nExpected, n);
    }
}
