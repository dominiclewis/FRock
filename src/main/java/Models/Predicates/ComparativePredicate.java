package Models.Predicates;

import Models.User;

import java.util.List;

import static Models.Predicates.BasePredicate.Predicate.GREATER_THAN;
import static Models.Sort.Attribute;
import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNull;

/**
 * Allows Comparative filtering to take place.
 */
public class ComparativePredicate
        extends BasePredicate
{
    final public static Predicate[] ALLOW_LISTS = {GREATER_THAN};

    /**
     * @param predicate - ALLOW_LIST contains valid values. ie. GREATER_THAN
     * @param attributes - format as follows ie. [PREDICATE.AGE, 10]
     *         Interpreted as AGE > 10
     */
    ComparativePredicate(final Predicate predicate, final List<Object> attributes)
    {
        super(predicate, attributes);
    }

    @Override
    public boolean filter(final User user)
    {
        // Attribute Predicate Attribute
        final Attribute attribute = Attribute.valueOf(attributes.get(0).toString());
        try
        {
            final int comparisonValue = parseInt(attributes.get(1).toString());
            if (predicate == GREATER_THAN)
            {
                return parseInt(user.getAttributes().get(attribute)) > comparisonValue;
            }
        }
        catch (NumberFormatException ex)
        {
            throw new RuntimeException("Bad comparative filter");
        }
        return false;
    }

    @Override
    Predicate[] getAllowList()
    {
        return ALLOW_LISTS;
    }

    @Override
    void setOperand(final List<Object> attributes)
    {
        requireNonNull(attributes, "Comparative predicate attribute set");
        if (attributes.size() == 2)
        {
            this.attributes.addAll(attributes);
        }
        else
        {
            throw new RuntimeException("Incorrect Comparative attribute size");
        }
    }
}
