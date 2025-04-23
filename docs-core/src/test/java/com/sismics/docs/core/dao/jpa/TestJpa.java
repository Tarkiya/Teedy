package com.sismics.docs.core.dao.jpa;

import com.sismics.docs.BaseTransactionalTest;
import com.sismics.docs.core.dao.UserDao;
import com.sismics.docs.core.model.jpa.User;
import com.sismics.docs.core.util.TransactionUtil;
import com.sismics.docs.core.util.authentication.InternalAuthenticationHandler;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the persistance layer.
 * 
 * @author jtremeaux
 */
public class TestJpa extends BaseTransactionalTest {
    @Test
    public void testJpa() throws Exception {
        // Create a user
        UserDao userDao = new UserDao();
        User user = createUser("testUpdatePassword");
        TransactionUtil.commit();

        String newPassword = "Old123456";
        user.setPassword(newPassword);
        User updatedUser = userDao.updatePassword(user, "testUserId");
        TransactionUtil.commit();

        // Search a user by his ID
        User newUser = userDao.getById(user.getId());
        Assert.assertNotNull(newUser);
        Assert.assertNotEquals("12345678", newUser.getPassword());

        // Authenticate using the database
//        Assert.assertNotNull(new InternalAuthenticationHandler().authenticate("testJpa", "12345678"));

        // Delete the created user
        userDao.delete("testUpdatePassword", user.getId());
        TransactionUtil.commit();
    }
}
