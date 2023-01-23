package Models;

import Models.Predicates.BasePredicate;
import Models.Sort.Attribute;
import lombok.Synchronized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElseGet;

/**
 * Store of all users known
 */
public class Users
{
    private static Users users;
    private static Map<UUID, User> ALL_USERS;

    private Users()
    { }

    @Synchronized
    public static Users getUsers(final Map<UUID, User> existingUsers)
    {
        if (users == null)
        {
            users = new Users();
            ALL_USERS = requireNonNullElseGet(existingUsers, HashMap::new);
        }
        return users;
    }

    /**
     * Creates or updates a user
     *
     * @param userId - uuid that identifies a user
     * @param attributes - ResourceKey, String map of attributes a given user can have
     *
     * @return user ID UUID
     */

    public UUID addAttributesToUser(final UUID userId, final Map<Attribute, String> attributes)
    {
        final User user;

        requireNonNull(attributes, "attribute map passed while trying to upsert");

        final Map<Attribute, String> validAttributes = getValidAttributes(attributes);

        // Get existing user or create new
        user = ALL_USERS.getOrDefault(userId, new User());

        // Add to their attributes
        for (Attribute attributeName : validAttributes.keySet())
        {
            user.upsert_attribute(attributeName, validAttributes.get(attributeName));
        }
        ALL_USERS.put(user.getUserId(), user);
        return user.getUserId();
    }

    /**
     * Retrieves user by ID
     *
     * @param userId
     *
     * @return User if valid id, null if invalid id
     */
    public User getUserById(final UUID userId)
    {
        requireNonNull(userId, "get user by id");
        return ALL_USERS.getOrDefault(userId, null);
    }

    /**
     * Get a user by a given filter search
     *
     * @param filters
     *
     * @return
     *
     * @see BasePredicate
     */
    public List<User> getUser(final List<BasePredicate> filters)
    {
        requireNonNull(filters, "Filters during userSearch");
        final List<User> matches = new ArrayList<>();
        boolean match;

        for (User user : ALL_USERS.values())
        {
            match = false;
            for (BasePredicate filter : filters)
            {
                // Check each user against a criteria
                match = filter.filter(user);
                if (!match)
                {
                    break;
                }
            }
            if (match)
            {
                matches.add(user);
            }
        }
        return matches;
    }

    /**
     * Retrieves users by given matches
     *
     * @param attributes - Filter criteria
     *
     * @return
     */
    public List<User> getUserByAttribute(final Map<Attribute, String> attributes)
    {
        requireNonNull(attributes, "attributes during user search");

        final List<User> matches = new ArrayList<>();
        boolean match;

        for (User user : ALL_USERS.values())
        {
            match = false;
            // For each user do they match the criteria
            for (Attribute attributeType : attributes.keySet())
            {
                // Does the user have the key?
                String userResult = user.getAttributes().get(attributeType);
                if (userResult != null)
                {
                    match = userResult.equals(attributes.get(attributeType));
                }
                if (!match || userResult == null)
                {
                    // If ever not a valid match then criteria incorrect
                    match = false;
                    break;
                }
            }
            if (match)
            {
                matches.add(user);
            }
        }
        return matches;
    }

    private Map<Attribute, String> getValidAttributes(final Map<Attribute, String> attributes)
    {
        final Map<Attribute, String> validAttributes = new HashMap<>();

        attributes.keySet()
                .stream()
                .filter(Objects::nonNull)
                .filter(key -> attributes.get(key) != null)
                .map(key -> validAttributes.put(key, attributes.get(key)))
                .collect(Collectors.toList());

        return validAttributes;
    }
}
