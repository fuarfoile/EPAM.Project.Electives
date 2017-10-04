import com.boast.model.dao.DaoFactory;
import com.boast.model.dao.connection.impl.MySqlConnectionFactory;
import com.boast.model.dao.impl.MySqlCourseDao;
import com.boast.model.dao.impl.MySqlDaoFactory;
import com.boast.domain.Course;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

public class MySqlCourseDaoTest {

    private Connection connection;
    private DaoFactory daoFactory;
    private Savepoint svpt;
    private MySqlCourseDao dao;

    @Before
    public void before() {
        daoFactory = MySqlDaoFactory.getInstance();
        try {
            connection = MySqlConnectionFactory.getInstance().getConnection();
            dao = daoFactory.getCourseDao(connection);
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
        List<Course> list = dao.getAll();

        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void testGetById() throws Exception {
        Course course = dao.getById(1);

        Assert.assertNotNull(course);
    }

    @Test
    public void testCreate() throws Exception {
        Course course = new Course();
        course.setId(9999);
        course.setName("Test name");
        course.setTeacherId(1);

        boolean res = dao.create(course);

        Assert.assertTrue(res);
    }

    @Test
    public void testUpdate() throws Exception {
        Course course = new Course();
        course.setId(1);
        course.setTeacherId(1);

        boolean res = dao.update(course);

        Assert.assertTrue(res);
    }

    @Test
    public void testDelete() throws Exception {
        Course course = new Course();
        course.setId(1);
        course.setTeacherId(1);

        boolean res = dao.delete(course);

        Assert.assertTrue(res);
    }
}
