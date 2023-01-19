package Models;

import static Utilities.Utils.nullChecker;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Model for users
 */
@Getter()
public class User
{
    private final UUID userId;
    private final Map<Sort.Attribute, String> attributes;

    public User()
    {
        userId = UUID.randomUUID();
        attributes = new HashMap<>();
    }

    /**
     * @see Sort.Attribute
     * @param value - Attribute value
     */
    public void upsert_attribute(final Sort.Attribute key, final String value)
    {
        nullChecker(key, "upsert user key");
        nullChecker(value, "upsert user value");
        attributes.put(key, value);
    }

    /**
     *
     */
}
