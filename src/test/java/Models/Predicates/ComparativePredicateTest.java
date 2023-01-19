package Models.Predicates;

import Models.Sort;
import Models.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

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
        user.upsert_attribute(Sort.Attribute.HEIGHT, "10");

        // What we're searching for
        final ArrayList<String> attributeStrings = new ArrayList<>();
        attributeStrings.add(Sort.Attribute.HEIGHT.toString());
        // How much
        attributeStrings.add("9");
        _comparativePredicate = new ComparativePredicate(BasePredicate.Predicate.GREATER_THAN, attributeStrings);
        assertTrue(_comparativePredicate.filter(user));
    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "Bad comparative filter")
    public void testNonIntAttribute()
    {
        // Create User
        final User user = new User();
        user.upsert_attribute(Sort.Attribute.JOB_TITLE, "Software Engineer");

        // What we're searching for
        final ArrayList<String> attributeStrings = new ArrayList<>();
        attributeStrings.add(Sort.Attribute.JOB_TITLE.toString());
        // How much
        attributeStrings.add("9");
        _comparativePredicate = new ComparativePredicate(BasePredicate.Predicate.GREATER_THAN, attributeStrings);
        _comparativePredicate.filter(user);
    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "Bad comparative filter")
    public void testNonIntAttributeFilterValue()
    {
        // Create User
        final User user = new User();
        user.upsert_attribute(Sort.Attribute.HEIGHT, "10");

        // What we're searching for
        final ArrayList<String> attributeStrings = new ArrayList<>();
        attributeStrings.add(Sort.Attribute.HEIGHT.toString());
        // How much
        attributeStrings.add("woof");
        _comparativePredicate = new ComparativePredicate(BasePredicate.Predicate.GREATER_THAN, attributeStrings);
        assertTrue(_comparativePredicate.filter(user));
    }

    @Test
    public void testFailingFilter()
    {
        // Create User
        final User user = new User();
        user.upsert_attribute(Sort.Attribute.HEIGHT, "9");

        // What we're searching for
        final ArrayList<String> attributeStrings = new ArrayList<>();
        attributeStrings.add(Sort.Attribute.HEIGHT.toString());
        // How much
        attributeStrings.add("10");
        _comparativePredicate = new ComparativePredicate(BasePredicate.Predicate.GREATER_THAN, attributeStrings);
        assertFalse(_comparativePredicate.filter(user));

    }

    @Test(expectedExceptions = RuntimeException.class,
            expectedExceptionsMessageRegExp =
                    "Null passed while trying to Comparative predicate attribute set")
    public void testSetAttributeNull()
    {
        _comparativePredicate = new ComparativePredicate(BasePredicate.Predicate.GREATER_THAN, null);
    }

    @Test(expectedExceptions = RuntimeException.class,
            expectedExceptionsMessageRegExp = "Null passed while trying to null predicate while setting search" +
                                              " predicate")
    public void testNullPredicate()
    {
        final User user = new User();
        final ArrayList<String> attributeList = new ArrayList<>();
        attributeList.add(Sort.Attribute.EMPLOYED.toString());
        user.upsert_attribute(Sort.Attribute.EMPLOYED, "true");
        _comparativePredicate = new ComparativePredicate(null, attributeList);
    }
}
