package Models.Predicates;

import Models.User;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static Models.Predicates.BasePredicate.Predicate.AND;
import static Models.Predicates.BasePredicate.Predicate.GREATER_THAN;
import static Models.Predicates.BasePredicate.Predicate.TRUE;
import static Models.Sort.Attribute.EMPLOYED;
import static Models.Sort.Attribute.HEIGHT;
import static java.util.Collections.emptyList;

public class ConnectivePredicateTest
{
    private ConnectivePredicate _connectivePredicate;

    @Test
    public void testFilter()
    {
        final User user = new User();
        user.upsert_attribute(EMPLOYED, "true");
        user.upsert_attribute(HEIGHT, "10");

        final List<Object> boolAttributes = new ArrayList<>();
        boolAttributes.add(EMPLOYED.toString());
        final BooleanPredicate booleanPredicate = new BooleanPredicate(TRUE, boolAttributes);

        final List<Object> compareAttributes = new ArrayList<>();
        compareAttributes.add(HEIGHT.toString());
        compareAttributes.add("9");
        final ComparativePredicate comparativePredicate = new ComparativePredicate(GREATER_THAN, compareAttributes);

        final List<Object> connectiveAttributes = new ArrayList<>();
        connectiveAttributes.add(booleanPredicate);
        connectiveAttributes.add(AND.toString());
        connectiveAttributes.add(comparativePredicate);
        Assert.assertTrue(new ConnectivePredicate(AND, connectiveAttributes).filter(user));
    }

    @Test
    public void testFailingFilter()
    {
        final User user = new User();
        user.upsert_attribute(EMPLOYED, "true");
        user.upsert_attribute(HEIGHT, "8");

        final List<Object> boolAttributes = new ArrayList<>();
        boolAttributes.add(EMPLOYED.toString());
        final BooleanPredicate booleanPredicate = new BooleanPredicate(TRUE, boolAttributes);

        final List<Object> compareAttributes = new ArrayList<>();
        compareAttributes.add(HEIGHT.toString());
        compareAttributes.add("9");
        final ComparativePredicate comparativePredicate = new ComparativePredicate(GREATER_THAN, compareAttributes);

        final List<Object> connectiveAttributes = new ArrayList<>();
        connectiveAttributes.add(booleanPredicate);
        connectiveAttributes.add(AND.toString());
        connectiveAttributes.add(comparativePredicate);
        Assert.assertFalse(new ConnectivePredicate(AND, connectiveAttributes).filter(user));
    }

    @Test
    public void testFilterBadAttributeOrder()
    {
        final User user = new User();
        user.upsert_attribute(EMPLOYED, "true");
        user.upsert_attribute(HEIGHT, "10");

        final List<Object> boolAttributes = new ArrayList<>();
        boolAttributes.add(EMPLOYED.toString());
        final BooleanPredicate booleanPredicate = new BooleanPredicate(TRUE, boolAttributes);

        final List<Object> compareAttributes = new ArrayList<>();
        compareAttributes.add(HEIGHT.toString());
        compareAttributes.add("9");
        final ComparativePredicate comparativePredicate = new ComparativePredicate(GREATER_THAN, compareAttributes);

        final List<Object> connectiveAttributes = new ArrayList<>();
        connectiveAttributes.add(booleanPredicate);
        connectiveAttributes.add(AND.toString());
        connectiveAttributes.add(comparativePredicate);
        Assert.assertTrue(new ConnectivePredicate(AND, connectiveAttributes).filter(user));
    }

    @Test(expectedExceptions = NullPointerException.class,
            expectedExceptionsMessageRegExp = "Connective Predicate attribute set")
    public void testNullAttributeList()
    {
        final User user = new User();
        user.upsert_attribute(EMPLOYED, "false");
        _connectivePredicate = new ConnectivePredicate(AND, null);
    }

    @Test(expectedExceptions = RuntimeException.class,
            expectedExceptionsMessageRegExp = "Incorrect connective attribute operand size")
    public void testEmptyAttributeList()
    {
        _connectivePredicate = new ConnectivePredicate(AND, emptyList());
    }

    @Test(expectedExceptions = RuntimeException.class,
            expectedExceptionsMessageRegExp = "null predicate while setting search predicate")
    public void testNullPredicate()
    {
        final ArrayList<Object> attributeList = new ArrayList<>();
        attributeList.add(EMPLOYED.toString());
        _connectivePredicate = new ConnectivePredicate(null, attributeList);
    }
}
