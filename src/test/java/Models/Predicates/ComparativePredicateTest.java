package Models.Predicates;

import Models.Sort;
import Models.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static Models.Predicates.BasePredicate.Predicate.GREATER_THAN;
import static Models.Sort.Attribute.EMPLOYED;
import static Models.Sort.Attribute.HEIGHT;
import static java.util.Collections.emptyList;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ComparativePredicateTest
{
    private ComparativePredicate _comparativePredicate;

    @BeforeMethod
    public void setup()
    {
        this._comparativePredicate = null;
    }

    @Test
    public void testFilter()
    {
        // Create User
        final User user = new User();
        user.upsert_attribute(HEIGHT, "10");

        // What we're searching for
        final ArrayList<Object> attributeStrings = new ArrayList<>();
        attributeStrings.add(HEIGHT.toString());
        // How much
        attributeStrings.add("9");
        _comparativePredicate = new ComparativePredicate(GREATER_THAN, attributeStrings);
        assertTrue(_comparativePredicate.filter(user));
    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "Bad comparative filter")
    public void testNonIntAttribute()
    {
        // Create User
        final User user = new User();
        user.upsert_attribute(Sort.Attribute.JOB_TITLE, "Software Engineer");

        // What we're searching for
        final ArrayList<Object> attributeStrings = new ArrayList<>();
        attributeStrings.add(Sort.Attribute.JOB_TITLE.toString());
        // How much
        attributeStrings.add("9");
        _comparativePredicate = new ComparativePredicate(GREATER_THAN, attributeStrings);
        _comparativePredicate.filter(user);
    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "Bad comparative filter")
    public void testNonIntAttributeFilterValue()
    {
        // Create User
        final User user = new User();
        user.upsert_attribute(HEIGHT, "10");

        // What we're searching for
        final ArrayList<Object> attributeStrings = new ArrayList<>();
        attributeStrings.add(HEIGHT.toString());
        // How much
        attributeStrings.add("woof");
        _comparativePredicate = new ComparativePredicate(GREATER_THAN, attributeStrings);
        assertTrue(_comparativePredicate.filter(user));
    }

    @Test
    public void testFailingFilter()
    {
        // Create User
        final User user = new User();
        user.upsert_attribute(HEIGHT, "9");

        // What we're searching for
        final ArrayList<Object> attributeStrings = new ArrayList<>();
        attributeStrings.add(HEIGHT.toString());
        // How much
        attributeStrings.add("10");
        _comparativePredicate = new ComparativePredicate(GREATER_THAN, attributeStrings);
        assertFalse(_comparativePredicate.filter(user));
    }

    @Test(expectedExceptions = NullPointerException.class,
            expectedExceptionsMessageRegExp = "Comparative predicate attribute set")
    public void testSetAttributeNull()
    {
        _comparativePredicate = new ComparativePredicate(GREATER_THAN, null);
    }

    @Test(expectedExceptions = NullPointerException.class,
            expectedExceptionsMessageRegExp = "null predicate while setting search predicate")
    public void testNullPredicate()
    {
        final User user = new User();
        final ArrayList<Object> attributeList = new ArrayList<>();
        attributeList.add(EMPLOYED.toString());
        user.upsert_attribute(EMPLOYED, "true");
        _comparativePredicate = new ComparativePredicate(null, attributeList);
    }

    @Test(expectedExceptions = RuntimeException.class,
            expectedExceptionsMessageRegExp = "Incorrect Comparative attribute size")
    public void testEmptyAttributeList()
    {
        _comparativePredicate = new ComparativePredicate(GREATER_THAN, emptyList());
    }
}
