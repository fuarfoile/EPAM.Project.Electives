import com.boast.domain.builder.impl.StudentCourseBuilder;
import com.boast.model.dao.DaoFactory;
import com.boast.model.dao.connection.impl.MySqlConnectionFactory;
import com.boast.model.dao.impl.MySqlDaoFactory;
import com.boast.model.dao.impl.MySqlStudentCourseDao;
import com.boast.domain.StudentCourse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

public class MySqlStudentCourseDaoTest {

    private Connection connection;
    private DaoFactory daoFactory;
    private Savepoint svpt;
    private MySqlStudentCourseDao dao;

    @Before
    public void before() {
        daoFactory = MySqlDaoFactory.getInstance();
        try {
            connection = MySqlConnectionFactory.getInstance().getConnection();
            dao = daoFactory.getStudentCourseDao(connection);
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
        List<StudentCourse> list = dao.getAll();

        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void testGetById() throws Exception {
        StudentCourse studentCourse = dao.getById(1);

        Assert.assertNotNull(studentCourse);
    }

    @Test
    public void testGetByIds() throws Exception {
        StudentCourse studentCourse = dao.getByIds(10, 10);

        Assert.assertNotNull(studentCourse);
    }

    @Test
    public void testCreate() throws Exception {
        StudentCourse studentCourse = new StudentCourseBuilder()
                .setStudentId(1)
                .setCourseId(1).build();

        boolean res = dao.create(studentCourse);

        Assert.assertTrue(res);
    }

    @Test
    public void testUpdate() throws Exception {
        StudentCourse studentCourse = new StudentCourseBuilder()
                .setId(1)
                .setStudentId(1)
                .setCourseId(1).build();

        boolean res = dao.update(studentCourse);

        Assert.assertTrue(res);
    }

    @Test
    public void testDelete() throws Exception {
        StudentCourse studentCourse = new StudentCourseBuilder()
                .setId(1).build();

        boolean res = dao.delete(studentCourse);

        Assert.assertTrue(res);
    }
}
