package Models.Predicates;

import Models.User;

import java.util.List;

public class ConnectivePredicate
        extends BasePredicate
{

    final public static BasePredicate.Predicate[] ALLOW_LISTS = {};

    ConnectivePredicate(final Predicate predicate, final List<String> attributes)
    {
        super(predicate, attributes);
    }

    @Override
    public boolean filter(User user)
    {
        return false;
    }

    @Override
    Predicate[] getAllowList()
    {
        return ALLOW_LISTS;
    }

    @Override
    void setOperand(List<String> attributes)
    {

    }
}
