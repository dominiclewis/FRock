package Models.Predicates;

import Models.Sort.Attribute;
import Models.User;

import java.util.List;

import static Models.Predicates.BasePredicate.Predicate.FALSE;
import static Models.Predicates.BasePredicate.Predicate.TRUE;
import static java.lang.Boolean.valueOf;
import static java.util.Objects.requireNonNull;

/**
 * Allows boolean filtering to take place.
 */
public class BooleanPredicate
        extends BasePredicate
{
    final public static Predicate[] ALLOW_LISTS = {TRUE, FALSE};

    /**
     * @param predicate - ALLOW_LIST contains valid values. ie. TRUE
     * @param attributes - format as follows ie. [Employed]
     *         Interpreted as Employed == True
     */
    BooleanPredicate(final Predicate predicate, final List<Object> attributes)
    {
        super(predicate, attributes);
    }

    @Override
    public boolean filter(final User user)
    {
        final Attribute attribute = Attribute.valueOf(attributes.get(0).toString());
        final String attributeValue = user.getAttributes().get(attribute);
        return valueOf(attributeValue) == valueOf(predicate.name());
    }

    @Override
    BasePredicate.Predicate[] getAllowList()
    {
        return ALLOW_LISTS;
    }

    @Override
    void setOperand(final List<Object> attributes)
    {
        requireNonNull(attributes, "Boolean predicate attribute set");
        if (attributes.size() == 1)
        {
            this.attributes.add(attributes.get(0));
        }
        else
        {
            throw new RuntimeException("Incorrect boolean attribute operand size");
        }
    }
}
