package Models;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class UserTest
{
    private User _user;

    @BeforeMethod
    void setup()
    {
        this._user = new User();
    }

    @Test
    public void testNewUserAssignedUUID()
    {
        // Create new user to compare with default
        User testUser = new User();
        assertNotEquals(testUser.getUserId(), _user.getUserId());
    }

    @Test
    public void testInsertAttributes()
    {
        final String forename = "Dom";
        final String jobTitle = "Software Engineer";
        _user.upsert_attribute(Sort.Attribute.FORENAME, forename);
        _user.upsert_attribute(Sort.Attribute.JOB_TITLE, jobTitle);
        assertEquals(_user.getAttributes().get(Sort.Attribute.FORENAME), forename);
        assertEquals(_user.getAttributes().get(Sort.Attribute.JOB_TITLE), jobTitle);
    }

    @Test
    public void testUpsertAttribute()
    {
        String forename = "Dom";
        _user.upsert_attribute(Sort.Attribute.FORENAME, forename);
        assertEquals(_user.getAttributes().get(Sort.Attribute.FORENAME), forename);

        forename = "NotDom";
        _user.upsert_attribute(Sort.Attribute.FORENAME, forename);
        assertEquals(_user.getAttributes().get(Sort.Attribute.FORENAME), forename);
    }

    @Test(expectedExceptions = RuntimeException.class,
            expectedExceptionsMessageRegExp = "Null passed while trying to upsert user key" )
    public void testNullUpsertKey()
    {
     _user.upsert_attribute(null, "foo");
    }

    @Test(expectedExceptions = RuntimeException.class,
            expectedExceptionsMessageRegExp = "Null passed while trying to upsert user value" )
    public void testNullUpsertValue()
    {
        _user.upsert_attribute(Sort.Attribute.JOB_TITLE, null);
    }
}
