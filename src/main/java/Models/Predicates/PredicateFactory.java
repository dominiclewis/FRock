package Models.Predicates;

import java.util.Arrays;
import java.util.List;

public class PredicateFactory
{
    private PredicateFactory()
    {
    }

    public static BasePredicate getPredicate(final BasePredicate.Predicate _predicate, final List<String> attributes)
    {
        if (Arrays.asList(BooleanPrediciate.ALLOW_LISTS).contains(_predicate))
        {
            return new BooleanPrediciate(_predicate, attributes);
        }

        if (Arrays.asList(ComparativePredicate.ALLOW_LISTS).contains(_predicate))
        {
            return new ComparativePredicate(_predicate, attributes);
        }

        if (Arrays.asList(ConnectivePredicate.ALLOW_LISTS).contains(_predicate))
        {
            return new ConnectivePredicate(_predicate, attributes);
        }
        return null;
    }
}
