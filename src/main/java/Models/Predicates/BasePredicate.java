package Models.Predicates;

import Models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Utilities.Utils.nullChecker;

public abstract class BasePredicate
{
    final protected List<String> attributes = new ArrayList<>();
    protected Predicate predicate;

    BasePredicate(final Predicate predicate, final List<String> attributes)
    {
        setPredicate(predicate);
        setOperand(attributes);
    }

    public abstract boolean filter(final User user);

    abstract BasePredicate.Predicate[] getAllowList();

    void setPredicate(final Predicate predicate)
    {
        nullChecker(predicate, "null predicate while setting search predicate");

        if (!Arrays.asList(getAllowList()).contains(predicate))
        {
            throw new RuntimeException("Incorrect predicate " + predicate.name());
        }
        this.predicate = predicate;
    }

    abstract void setOperand(final List<String> attributes);

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
