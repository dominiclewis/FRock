package Models.Predicates;

import Models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Base class containing common functionality of each predicate and establishing usage pattern.
 */
public abstract class BasePredicate
{
    final protected List<Object> attributes = new ArrayList<>();
    protected Predicate predicate;

    BasePredicate(final Predicate predicate, final List<Object> attributes)
    {
        setPredicate(predicate);
        setOperand(attributes);
    }

    public abstract boolean filter(final User user);

    abstract Predicate[] getAllowList();

    void setPredicate(final Predicate predicate)
    {
        requireNonNull(predicate, "null predicate while setting search predicate");

        if (!Arrays.asList(getAllowList()).contains(predicate))
        {
            throw new RuntimeException("Incorrect predicate " + predicate.name());
        }
        this.predicate = predicate;
    }

    abstract void setOperand(final List<Object> attributes);

    public enum Predicate
    {
        //Boolean
        TRUE("True"),
        FALSE("False"),
        // Connective
        AND("And"),
        // Comparative
        GREATER_THAN("Greater Than");

        Predicate(final String value) { }
    }
}
