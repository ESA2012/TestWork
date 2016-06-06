package esa2012.datalayer;

import esa2012.model.Department;
import esa2012.datalayer.interfaces.DAOException;
import esa2012.datalayer.interfaces.DepartmentDAO;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;



public class DepartmentDAOImpl implements DepartmentDAO {

    protected Connection getConnection() throws DAOException {
        try {
            return  DBService.getInstance().getConnection();
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }


    @Override
    public List<Department> getAll(boolean includeEmployees) throws DAOException {
        String sql_str = "SELECT * FROM dep ORDER BY dep_id";
        List<Department> result;
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql_str);
            ResultSet resultSet = statement.executeQuery();
            result = new LinkedList<>();
            while (resultSet.next()) {
                Department department = getDepartment(resultSet, includeEmployees);
                result.add(department);
            }
            DBService.close(statement, resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    public void add(Department department) throws DAOException {
        String sql_str = "INSERT INTO dep VALUES (DEFAULT, '"+department.getDepName()+"')";
        try (Connection connection = getConnection()){
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql_str, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                department.setId(resultSet.getInt(1));
            }
            DBService.close(statement, resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void remove(Integer id) throws DAOException {
        String sql_str = "DELETE FROM dep WHERE dep_id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql_str);
            statement.setInt(1, id);
            statement.executeUpdate();
            DBService.close(statement, null);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Department department) throws DAOException {
        String sql_str = "UPDATE dep SET dep_name = ? WHERE dep_id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql_str);
            statement.setString(1, department.getDepName());
            statement.setInt(2, department.getId());
            statement.executeUpdate();
            DBService.close(statement, null);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Department getById(Integer id, boolean includeEmployees) throws DAOException {
        String sql_str = "SELECT * FROM dep WHERE dep_id = ?";
        Department department = null;
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql_str);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                department = getDepartment(resultSet, includeEmployees);
            }
            DBService.close(statement, resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return department;
    }

    private Department getDepartment(ResultSet resultSet, boolean includeEmployees) throws SQLException, DAOException {
        Department department;
        department = new Department();
        department.setId(resultSet.getInt("dep_id"));
        department.setDepName(resultSet.getString("dep_name"));
        if (includeEmployees) {
            department.setEmployees(new EmployeeDAOImpl().getByDepartmentId(department.getId()));
        }
        return department;
    }
}
