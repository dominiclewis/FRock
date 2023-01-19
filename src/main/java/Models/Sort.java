package Models;

public class Sort
{
    /**
     * Possible fields a user attribute can be.
     */
    public enum Attribute
    {
        FORENAME("Forename"),
        SURNAME("Surname"),
        TITLE("Title"),
        JOB_TITLE("JobTitle"),
        SEX("Sex"),
        EMPLOYED("Employed"),
        HEIGHT("Height");

        Attribute(final String value) { }
    }
}
