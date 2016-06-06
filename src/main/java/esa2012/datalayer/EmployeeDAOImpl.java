package esa2012.datalayer;

import esa2012.datalayer.interfaces.DAOException;
import esa2012.datalayer.interfaces.EmployeeDAO;
import esa2012.model.Department;
import esa2012.model.Employee;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by snake on 31.05.16.
 */
public class EmployeeDAOImpl implements EmployeeDAO {

    protected Connection getConnection() throws DAOException {
        try {
            return  DBConnections.getConnection();
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void add(Employee employee) throws DAOException {
        String sql_str = "INSERT INTO empl VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql_str, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, employee.getDepId());
            statement.setString(2, employee.getFirstName());
            statement.setString(3, employee.getLastName());
            Date date = employee.getDateOfBirth() == null? null : new Date(employee.getDateOfBirth().getTime());
            statement.setDate(4, date);
            statement.setString(5, employee.getEmail());
            statement.setString(6, employee.getPosition());
            statement.setBigDecimal(7, employee.getSalary());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                employee.setId(resultSet.getInt(1));
            }
            DBUtils.close(statement, resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void remove(Integer id) throws DAOException {
        String sql_str = "DELETE FROM empl WHERE emp_id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql_str);
            statement.setInt(1, id);
            statement.executeUpdate();
            DBUtils.close(statement, null);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Employee employee) throws DAOException {
        String sql_str = "UPDATE empl SET dep_id = ?, " +
                "emp_firstname = ?, " +
                "emp_lastname = ?, " +
                "emp_birthdate = ?, " +
                "emp_email = ?, " +
                "emp_post = ?, " +
                "emp_salary = ? " +
                "WHERE emp_id = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql_str);
            statement.setInt(8, employee.getId());
            statement.setInt(1, employee.getDepId());
            statement.setString(2, employee.getFirstName());
            statement.setString(3, employee.getLastName());
            Date date = employee.getDateOfBirth() == null? null : new Date(employee.getDateOfBirth().getTime());
            statement.setDate(4, date);
            statement.setString(5, employee.getEmail());
            statement.setString(6, employee.getPosition());
            statement.setBigDecimal(7, employee.getSalary());
            statement.executeUpdate();
            DBUtils.close(statement, null);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Employee> getByDepartment(Department department) throws DAOException {
        return getByDepartmentId(department.getId());
    }


    @Override
    public List<Employee> getByDepartmentId(Integer departmentId) throws DAOException {
        String sql_str = "SELECT * FROM empl WHERE dep_id = ?";
        List<Employee> result;
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql_str);
            statement.setInt(1, departmentId);
            ResultSet resultSet = statement.executeQuery();
            result = new LinkedList<>();
            while (resultSet.next()) {
                Employee empl = getEmployee(resultSet);
                result.add(empl);
            }
            DBUtils.close(statement, resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return result;
    }


    @Override
    public Employee getById(Integer id) throws DAOException {
        String sql_str = "SELECT * FROM empl WHERE emp_id = ?";
        Employee empl = null;
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql_str);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                empl = getEmployee(resultSet);
            }
            DBUtils.close(statement, resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return empl;
    }



    @Override
    public List<String> getEmails() throws DAOException {
        List<String> result = new LinkedList<>();
        String sql_str = "SELECT emp_email FROM empl";
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql_str);
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
            DBUtils.close(statement, resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return result;
    }


    private Employee getEmployee(ResultSet resultSet) throws SQLException {
        Employee empl = new Employee();
        empl.setId(resultSet.getInt("emp_id"));
        empl.setFirstName(resultSet.getString("emp_firstname"));
        empl.setLastName(resultSet.getString("emp_lastname"));
        empl.setEmail(resultSet.getString("emp_email"));
        empl.setPosition(resultSet.getString("emp_post"));
        empl.setSalary(resultSet.getBigDecimal("emp_salary"));
        empl.setDateOfBirth(resultSet.getDate("emp_birthdate"));
        empl.setDepId(resultSet.getInt("dep_id"));
        return empl;
    }


}
