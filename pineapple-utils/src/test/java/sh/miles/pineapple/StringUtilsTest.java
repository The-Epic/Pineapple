package sh.miles.pineapple;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void test_Should_Split_Correctly() {
        String string0 = "hello-world-we-are-splitting-it-up";
        System.out.println(Arrays.toString(StringUtils.split(string0, '-')));
        assertEquals(7, StringUtils.split(string0, '-').length);

        String string1 = "-w---";
        System.out.println(Arrays.toString(StringUtils.split(string1, '-')));
        assertEquals(4, StringUtils.split(string1, '-').length);
    }

}
