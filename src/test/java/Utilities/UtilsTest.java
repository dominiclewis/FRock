package Utilities;

import org.testng.annotations.Test;

import static Utilities.Utils.nullChecker;

@Test
public class UtilsTest
{
    @Test
    public void testNonNullValueNullChecker()
    {
        nullChecker("foo", "test non null string");
    }

    @Test(expectedExceptions = RuntimeException.class,
            expectedExceptionsMessageRegExp = "Null passed while trying to test null string")
    public void testNullValueNullChecker()
    {
        nullChecker(null, "test null string");
    }
}
