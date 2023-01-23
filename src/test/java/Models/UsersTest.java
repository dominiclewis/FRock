package Models;

import Models.Predicates.BasePredicate;
import Models.Predicates.PredicateFactory;
import Models.Sort.Attribute;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static Models.Predicates.BasePredicate.Predicate.FALSE;
import static Models.Predicates.BasePredicate.Predicate.TRUE;
import static Models.Sort.Attribute.EMPLOYED;
import static Models.Sort.Attribute.FORENAME;
import static Models.Sort.Attribute.JOB_TITLE;
import static Models.Users.getUsers;
import static java.lang.String.valueOf;
import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNull;
import static org.testng.AssertJUnit.assertEquals;

public class UsersTest
{
    @Spy
    private HashMap<UUID, User> _all_users;

    private Users _users;

    private Map<Attribute, String> _attributes;

    @BeforeMethod
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        _all_users.clear();
        this._users = getUsers(_all_users);
        this._attributes = new HashMap<>();
    }

    @Test
    public void testCreateSingleUser()
    {
        final String forename = "Dom";
        final String jobTitle = "Software Engineer";

        _attributes.put(FORENAME, forename);
        _attributes.put(JOB_TITLE, jobTitle);

        final UUID uuid = _users.addAttributesToUser(null, _attributes);

        // Try to get user using UUID
        User user = _users.getUserById(uuid);
        assertEquals(user.getUserId(), uuid);
    }

    @Test(expectedExceptions = NullPointerException.class,
            expectedExceptionsMessageRegExp = "attributes during user search")
    public void testGetUserByAttributeNullAttribute()
    {
        _users.getUserByAttribute(null);
    }

    @Test
    public void testGetUserByAttribute()
    {
        final String forename = "Dom";
        _attributes.put(FORENAME, forename);

        _users.addAttributesToUser(null, _attributes);
        final List<User> result = _users.getUserByAttribute(_attributes);

        assertEquals(1, result.size());
        assertEquals(_attributes, result.get(0).getAttributes());
    }

    @Test
    public void testMultipleGetUserByAttribute()
    {
        final String jobTitle = "Software Engineer";
        final List<String> forenames = new ArrayList<>();
        forenames.add("Dom");
        forenames.add("Bob");
        forenames.add("Alice");

        // Matches
        for (int i = 0; i < forenames.size() - 1; i++)
        {
            _attributes.put(FORENAME, forenames.get(i));
            _attributes.put(JOB_TITLE, jobTitle);
            _users.addAttributesToUser(null, _attributes);
            _attributes = new HashMap<>();
        }

        // Non matches
        _attributes.put(FORENAME, forenames.get(2));
        _users.addAttributesToUser(null, _attributes);

        // Retrieve matches
        _attributes = new HashMap<>();
        _attributes.put(JOB_TITLE, jobTitle);

        final List<User> result = _users.getUserByAttribute(_attributes);

        assertEquals(result.size(), 2);
        for (User user : result)
        {
            assertEquals(user.getAttributes().get(JOB_TITLE), jobTitle);
        }
    }

    @Test
    public void testGetUserSingleWithMissingAttribute()
    {
        final String forename = "Dom";
        _attributes.put(FORENAME, forename);
        _users.addAttributesToUser(null, _attributes);

        _attributes.put(JOB_TITLE, "Software Engineer");
        final List<User> result = _users.getUserByAttribute(_attributes);

        assertEquals(result.size(), 0);
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "get user by id")
    public void getNullUser()
    {
        _users.getUserById(null);
    }

    @Test
    public void getUnknownUser()
    {
        assertNull(_users.getUserById(randomUUID()));
    }

    @Test
    public void getKnownUser()
    {
        final User injectedUser = new User();
        when(_all_users.getOrDefault(any(UUID.class), eq(null)))
                .thenReturn(injectedUser);
        final User user = _users.getUserById(randomUUID());
        assertEquals(user.getUserId(), injectedUser.getUserId());
    }

    @Test(expectedExceptions = NullPointerException.class,
            expectedExceptionsMessageRegExp = "attribute map passed while trying to upsert")
    public void testCreateSingleUserWithNullAttributes()
    {
        _users.addAttributesToUser(randomUUID(), null);
    }

    @Test
    public void getUserValid()
    {
        final boolean employed = true;
        _attributes.put(EMPLOYED, valueOf(employed));
        _users.addAttributesToUser(null, _attributes);

        _attributes.clear();
        _attributes.put(EMPLOYED, "false");
        _users.addAttributesToUser(null, _attributes);

        assertEquals(2, _all_users.size());
        // What are we looking for?
        final List<Object> searchAttributes = new ArrayList<>();
        searchAttributes.add(EMPLOYED.name());

        // What do we want it to look like?
        final BasePredicate boolPred = PredicateFactory.getPredicate(TRUE, searchAttributes);

        final List<BasePredicate> filters = new ArrayList<>();
        filters.add(boolPred);

        final List<User> result = _users.getUser(filters);
        assertEquals(1, result.size());
    }

    @Test
    public void getInvalidUser()
    {
        final boolean employed = true;
        _attributes.put(EMPLOYED, valueOf(employed));
        _users.addAttributesToUser(null, _attributes);

        // What are we looking for?
        final List<Object> searchAttributes = new ArrayList<>();
        searchAttributes.add(EMPLOYED.name());

        // What do we want it to look like?
        final BasePredicate boolPred = PredicateFactory.getPredicate(FALSE, searchAttributes);

        final List<BasePredicate> filters = new ArrayList<>();
        filters.add(boolPred);

        final List<User> result = _users.getUser(filters);
        assertEquals(0, result.size());
    }

}
