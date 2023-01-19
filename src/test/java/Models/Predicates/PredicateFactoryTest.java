package Models.Predicates;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PredicateFactoryTest
{
    private final List<String> attributes = new ArrayList<>();

    @DataProvider(name = "generateComparativePredicates")
    public Iterator<BasePredicate.Predicate> generateComparativePredicates()
    {
        return Arrays.stream(ComparativePredicate.ALLOW_LISTS).iterator();
    }

    @DataProvider(name = "generateBooleanPredicates")
    public Iterator<BasePredicate.Predicate> generateBooleanPredicates()
    {
        return Arrays.stream(BooleanPrediciate.ALLOW_LISTS).iterator();
    }

    @DataProvider(name = "generateConnectivePredicates")
    public Iterator<BasePredicate.Predicate> generateConnectivePredicates()
    {
        return Arrays.stream(ConnectivePredicate.ALLOW_LISTS).iterator();
    }

    @Test(dataProvider = "generateComparativePredicates")
    public void testGetComparativePredicate(final BasePredicate.Predicate predicate)
    {
        Assert.assertTrue(PredicateFactory.getPredicate(predicate, attributes) instanceof ComparativePredicate);
    }

    @Test(dataProvider = "generateBooleanPredicates")
    public void testGetBooleanPredicate(final BasePredicate.Predicate predicate)
    {
        Assert.assertTrue(PredicateFactory.getPredicate(predicate, attributes) instanceof BooleanPrediciate);
    }

    @Test(dataProvider = "generateConnectivePredicates")
    public void testGetConnectivePredicate(final BasePredicate.Predicate predicate)
    {
        Assert.assertTrue(PredicateFactory.getPredicate(predicate, attributes) instanceof ConnectivePredicate);
    }
}
