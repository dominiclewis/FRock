package Utilities;

public class Utils
{
    private Utils() { }

    public static <T> void nullChecker(T arg, String scenario)
    {
        if (arg == null)
        {
            throw new RuntimeException("Null passed while trying to " + scenario);
        }
    }
}
