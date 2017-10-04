import com.boast.model.dao.connection.impl.MySqlConnectionFactory;
import com.boast.model.dao.impl.MySqlDaoFactory;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

public class MySqlDaoFactoryTest {

    @Test
    public void testGetConnection() throws Exception {
        MySqlDaoFactory daoFactory = MySqlDaoFactory.getInstance();
        Connection connection = MySqlConnectionFactory.getInstance().getConnection();

        Assert.assertNotNull(connection);
    }
}
