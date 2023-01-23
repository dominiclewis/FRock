package Models.Predicates;

import Models.User;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Allows Connective filtering to take place.
 */
public class ConnectivePredicate
        extends BasePredicate
{

    final public static BasePredicate.Predicate[] ALLOW_LISTS = {Predicate.AND};

    /**
     * @param predicate - ALLOW_LIST contains valid values. ie. AND
     * @param attributes - format as follows ie. [PRED1, PRED2...]
     *         Interpreted as PRED1 AND PRED2
     */
    ConnectivePredicate(final Predicate predicate, final List<Object> attributes)
    {
        super(predicate, attributes);
    }

    @Override
    public boolean filter(final User user)
    {
        // boolean predicate AND comparative predicate AND x predicate
        boolean result = false;
        for (Object obj : this.attributes)
        {
            if (obj instanceof ConnectivePredicate)
            {
                throw new RuntimeException("Circular predicate filter");
            }
            else if (obj instanceof BasePredicate)
            {
                result = ((BasePredicate) obj).filter(user);
            }
            else if (obj instanceof String)
            {
                if (obj.equals(Predicate.AND.toString()))
                {
                    // NOP AND
                    continue;
                }
            }
        }
        return result;
    }

    @Override
    Predicate[] getAllowList()
    {
        return ALLOW_LISTS;
    }

    @Override
    void setOperand(final List<Object> attributes)
    {
        requireNonNull(attributes, "Connective Predicate attribute set");
        if (attributes.size() > 2)
        {
            this.attributes.addAll(attributes);
        }
        else
        {
            throw new RuntimeException("Incorrect connective attribute operand size");
        }
    }
}
