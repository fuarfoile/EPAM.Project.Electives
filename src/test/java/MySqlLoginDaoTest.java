import com.boast.model.dao.DaoFactory;
import com.boast.model.dao.connection.impl.MySqlConnectionFactory;
import com.boast.model.dao.impl.MySqlDaoFactory;
import com.boast.model.dao.impl.MySqlLoginDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class MySqlLoginDaoTest {

    private Connection connection;
    private DaoFactory daoFactory;
    private MySqlLoginDao dao;

    @Before
    public void before() {
        daoFactory = MySqlDaoFactory.getInstance();

        connection = MySqlConnectionFactory.getInstance().getConnection();
        dao = daoFactory.getLoginDao(connection);
    }

    @Test
    public void testValidate() throws Exception {
        Assert.assertNotNull(dao.getUser("admin@admin", "admin"));
        Assert.assertNull(dao.getUser("admin@admin", ""));
        Assert.assertNull(dao.getUser("admin@admin", "test"));
        Assert.assertNull(dao.getUser("admin", "admin"));
    }
}
