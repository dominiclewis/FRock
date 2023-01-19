package Models.Predicates;

import Models.Predicates.BasePredicate.Predicate;
import Models.Sort;
import Models.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class BooleanPredicateTest
{
    private BooleanPrediciate _booleanPredicate;

    @BeforeMethod
    public void setup()
    {
        this._booleanPredicate = null;
    }

    @Test
    public void testFilter()
    {
        final User user = new User();
        user.upsert_attribute(Sort.Attribute.EMPLOYED, "true");

        final ArrayList<String> attributeStrings = new ArrayList<>();
        attributeStrings.add(Sort.Attribute.EMPLOYED.toString());

        _booleanPredicate = new BooleanPrediciate(Predicate.TRUE, attributeStrings);
        assertTrue(_booleanPredicate.filter(user));
    }

    @Test
    public void testNonBooleanFiler()
    {
        final User user = new User();
        user.upsert_attribute(Sort.Attribute.EMPLOYED, "true");

        final ArrayList<String> attributeStrings = new ArrayList<>();
        attributeStrings.add(Sort.Attribute.EMPLOYED.toString());

        _booleanPredicate = new BooleanPrediciate(Predicate.TRUE, attributeStrings);
        assertTrue(_booleanPredicate.filter(user));
    }

    @Test
    public void testFailingFilter()
    {
        final User user = new User();
        final ArrayList<String> attributeList = new ArrayList<>();
        attributeList.add(Sort.Attribute.EMPLOYED.toString());

        user.upsert_attribute(Sort.Attribute.EMPLOYED, "false");
        _booleanPredicate = new BooleanPrediciate(Predicate.TRUE, attributeList);
        assertFalse(_booleanPredicate.filter(user));
    }

    @Test(expectedExceptions = RuntimeException.class,
            expectedExceptionsMessageRegExp = "Null passed while trying to Boolean predicate attribute set")
    public void testNullAttributeList()
    {
        final User user = new User();

        user.upsert_attribute(Sort.Attribute.EMPLOYED, "false");
        _booleanPredicate = new BooleanPrediciate(Predicate.TRUE, null);
        assertFalse(_booleanPredicate.filter(user));
    }

    @Test(expectedExceptions = RuntimeException.class,
            expectedExceptionsMessageRegExp =
                    "Null passed while trying to null predicate while setting search predicate")
    public void testNullPredicate()
    {
        final User user = new User();
        final ArrayList<String> attributeList = new ArrayList<>();
        attributeList.add(Sort.Attribute.EMPLOYED.toString());
        user.upsert_attribute(Sort.Attribute.EMPLOYED, "true");
        _booleanPredicate = new BooleanPrediciate(null, attributeList);
    }

    @Test(expectedExceptions = RuntimeException.class,
            expectedExceptionsMessageRegExp =
                    "Null passed while trying to Boolean predicate attribute set")
    public void testSetAttributeNull()
    {
        _booleanPredicate = new BooleanPrediciate(Predicate.FALSE, null);
    }
}
