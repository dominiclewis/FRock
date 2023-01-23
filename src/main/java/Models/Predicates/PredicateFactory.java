package Models.Predicates;

import java.util.List;

import static Models.Predicates.BasePredicate.Predicate;
import static java.util.Arrays.asList;

/**
 * Public interface for interacting with filtering predicates.
 */
public class PredicateFactory
{
    private PredicateFactory()
    {
    }

    public static BasePredicate getPredicate(final Predicate _predicate, final List<Object> attributes)
    {
        if (asList(BooleanPredicate.ALLOW_LISTS).contains(_predicate))
        {
            return new BooleanPredicate(_predicate, attributes);
        }

        if (asList(ComparativePredicate.ALLOW_LISTS).contains(_predicate))
        {
            return new ComparativePredicate(_predicate, attributes);
        }

        if (asList(ConnectivePredicate.ALLOW_LISTS).contains(_predicate))
        {
            return new ConnectivePredicate(_predicate, attributes);
        }
        return null;
    }
}
