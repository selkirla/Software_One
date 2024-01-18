import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * @author Selin Kirbas
 *
 */
public class StringReassemblyTest {
    /**
     * Tests of combination.
     */
    @Test
    public void combination() {

        String str1 = "abcde";
        String str2 = "defg";
        int overlap = 2;

        String result = StringReassembly.combination(str1, str2, overlap);
        assertEquals("abcdefg", result);
    }

    public void combination2() {

        String str1 = "abcde";
        String str2 = "fg";
        int overlap = 0;

        String result = StringReassembly.combination(str1, str2, overlap);
        assertEquals("abcdefg", result);
    }

    /**
     * Tests of printWithLineSeparators.
     */
    @Test
    public void printWithLineSeparators() {
        SimpleWriter out = new SimpleWriter1L();
        String text = "The quick brown ~ fox jumps over ~ the lazy dog.";

        StringReassembly.printWithLineSeparators(text, out);
    }

}
