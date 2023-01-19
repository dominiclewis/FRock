package Models.Predicates;

import Models.Sort.Attribute;
import Models.User;

import java.util.List;

import static Utilities.Utils.nullChecker;

public class BooleanPrediciate
        extends BasePredicate
{
    final public static BasePredicate.Predicate[] ALLOW_LISTS = {Predicate.TRUE, Predicate.FALSE};

    BooleanPrediciate(final Predicate predicate, final List<String> attributes)
    {
        super(predicate, attributes);
    }

    @Override
    public boolean filter(final User user)
    {
        final Attribute attribute = Attribute.valueOf(attributes.get(0));
        final String attributeValue = user.getAttributes().get(attribute);
        return Boolean.valueOf(attributeValue) == Boolean.valueOf(predicate.name());
    }

    @Override
    BasePredicate.Predicate[] getAllowList()
    {
        return ALLOW_LISTS;
    }

    @Override
    void setOperand(final List<String> attributes)
    {
        nullChecker(attributes, "Boolean predicate attribute set");
        if (attributes.size() == 1)
        {
            this.attributes.add(attributes.get(0));
        }
        // TODO - Throw exception
    }
}
