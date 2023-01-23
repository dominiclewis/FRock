package Models.Predicates;

import Models.Predicates.BasePredicate.Predicate;
import Models.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static Models.Predicates.BasePredicate.Predicate.TRUE;
import static Models.Sort.Attribute.EMPLOYED;
import static java.util.Collections.emptyList;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class BooleanPredicateTest
{
    private BooleanPredicate _booleanPredicate;

    @BeforeMethod
    public void setup()
    {
        this._booleanPredicate = null;
    }

    @Test
    public void testFilter()
    {
        final User user = new User();
        user.upsert_attribute(EMPLOYED, "true");

        final ArrayList<Object> attributeStrings = new ArrayList<>();
        attributeStrings.add(EMPLOYED.toString());

        _booleanPredicate = new BooleanPredicate(TRUE, attributeStrings);
        assertTrue(_booleanPredicate.filter(user));
    }

    @Test
    public void testNonBooleanFiler()
    {
        final User user = new User();
        user.upsert_attribute(EMPLOYED, "true");

        final ArrayList<Object> attributeStrings = new ArrayList<>();
        attributeStrings.add(EMPLOYED.toString());

        _booleanPredicate = new BooleanPredicate(TRUE, attributeStrings);
        assertTrue(_booleanPredicate.filter(user));
    }

    @Test
    public void testFailingFilter()
    {
        final User user = new User();
        final ArrayList<Object> attributeList = new ArrayList<>();
        attributeList.add(EMPLOYED.toString());

        user.upsert_attribute(EMPLOYED, "false");
        _booleanPredicate = new BooleanPredicate(TRUE, attributeList);
        assertFalse(_booleanPredicate.filter(user));
    }

    @Test(expectedExceptions = NullPointerException.class,
            expectedExceptionsMessageRegExp = "Boolean predicate attribute set")
    public void testNullAttributeList()
    {
        final User user = new User();

        user.upsert_attribute(EMPLOYED, "false");
        _booleanPredicate = new BooleanPredicate(TRUE, null);
    }

    @Test(expectedExceptions = NullPointerException.class,
            expectedExceptionsMessageRegExp = "null predicate while setting search predicate")
    public void testNullPredicate()
    {
        final ArrayList<Object> attributeList = new ArrayList<>();
        attributeList.add(EMPLOYED.toString());
        _booleanPredicate = new BooleanPredicate(null, attributeList);
    }

    @Test(expectedExceptions = RuntimeException.class,
            expectedExceptionsMessageRegExp = "Boolean predicate attribute set")
    public void testSetAttributeNull()
    {
        _booleanPredicate = new BooleanPredicate(Predicate.FALSE, null);
    }

    @Test(expectedExceptions = RuntimeException.class,
            expectedExceptionsMessageRegExp = "Incorrect boolean attribute operand size")
    public void testEmptyAttributeList()
    {
        _booleanPredicate = new BooleanPredicate(Predicate.FALSE, emptyList());
    }
}
