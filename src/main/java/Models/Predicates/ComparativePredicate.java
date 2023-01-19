package Models.Predicates;

import Models.Sort;
import Models.User;

import java.util.List;

import static Utilities.Utils.nullChecker;

public class ComparativePredicate
        extends BasePredicate
{
    final public static BasePredicate.Predicate[] ALLOW_LISTS = {BasePredicate.Predicate.GREATER_THAN};

    ComparativePredicate(final Predicate predicate, final List<String> attributes)
    {
        super(predicate, attributes);
    }

    @Override
    public boolean filter(final User user)
    {
        // Attribute Predicate Attribute
        final Sort.Attribute attribute = Sort.Attribute.valueOf(attributes.get(0));
        try
        {
            final int comparisonValue = Integer.parseInt(attributes.get(1));
            if (predicate == Predicate.GREATER_THAN)
            {
                return Integer.parseInt(user.getAttributes().get(attribute)) > comparisonValue;
            }
        }
        catch (NumberFormatException ex)
        {
            throw new RuntimeException("Bad comparative filter");
        }
        return false;
    }

    @Override
    BasePredicate.Predicate[] getAllowList()
    {
        return ALLOW_LISTS;
    }

    @Override
    void setOperand(final List<String> attributes)
    {
        nullChecker(attributes, "Comparative predicate attribute set");
        if (attributes.size() == 2)
        {
            this.attributes.add(attributes.get(0));
            this.attributes.add(attributes.get(1));
        }
    }
}
