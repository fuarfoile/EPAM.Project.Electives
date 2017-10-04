import com.boast.model.dao.DaoFactory;
import com.boast.model.dao.connection.impl.MySqlConnectionFactory;
import com.boast.model.dao.impl.MySqlDaoFactory;
import com.boast.model.dao.impl.MySqlUserDao;
import com.boast.domain.Position;
import com.boast.domain.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

public class MySqlUserDaoTest {

    private Connection connection;
    private DaoFactory daoFactory;
    private Savepoint svpt;
    private MySqlUserDao dao;

    @Before
    public void before() {
        daoFactory = MySqlDaoFactory.getInstance();
        try {
            connection = MySqlConnectionFactory.getInstance().getConnection();
            dao = daoFactory.getUserDao(connection);
            connection.setAutoCommit(false);
            svpt = connection.setSavepoint("NewEmp");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @After
    public void after() {
        try {
            connection.rollback(svpt);
            connection.setAutoCommit(true);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Test
    public void testGetAll() throws Exception {
        List<User> list = dao.getAll();

        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void testGetById() throws Exception {
        User user = dao.getById(1);

        Assert.assertNotNull(user);
    }

    @Test
    public void testCreate() throws Exception {
        User user = new User();
        user.setName("Test name");
        user.setEmail("test@email");
        user.setPosition(Position.ADMIN);

        boolean res = dao.create(user);

        Assert.assertTrue(res);
    }

    @Test
    public void testUpdate() throws Exception {
        User user = new User();
        user.setId(1);
        user.setEmail("test@email");
        user.setPosition(Position.ADMIN);

        boolean res = dao.update(user);

        Assert.assertTrue(res);
    }

    @Test
    public void testDelete() throws Exception {
        User user = new User();
        user.setId(1);

        boolean res = dao.delete(user);

        Assert.assertTrue(res);
    }
}
