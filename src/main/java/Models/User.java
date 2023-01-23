package Models;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static Models.Sort.Attribute;

/**
 * Model for users
 */
@Getter()
public class User
{
    private final UUID userId;
    private final Map<Attribute, String> attributes;

    public User()
    {
        userId = UUID.randomUUID();
        attributes = new HashMap<>();
    }

    /**
     * @param value - Attribute value
     *
     * @see Attribute
     */
    public void upsert_attribute(final Attribute key, final String value)
    {
        Objects.requireNonNull(key, "upsert user key");
        Objects.requireNonNull(value, "upsert user value");
        attributes.put(key, value);
    }
}
