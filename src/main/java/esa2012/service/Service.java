package esa2012.service;

import esa2012.model.Department;
import esa2012.model.Employee;
import esa2012.service.datatransport.ErrorMessages;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class Service {

    private final static Logger logger = LogManager.getLogger("TestWork app");

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private Service() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();

            File fileDB = new File(classLoader.getResource("tables.sql").getFile());
            File fileDemoData = new File(classLoader.getResource("demo_data.sql").getFile());

            Connection connection= DBService.getInstance().getConnection();

            DBService.executeSQLfile(fileDB, connection);
            DBService.executeSQLfile(fileDemoData, connection);
            DBService.close(connection);

        } catch (Exception e) {
            logger.error("Unable to initialize data base");
        }
    }

    public static final Service INSTANCE = new Service();


    private Connection getConnection() throws SQLException {
        return DBService.getInstance().getConnection();
    }


    /**
     * Returns list of departments
     * @return  list of departments
     */
    public List<Department> getDepartments() {
        String sql_str = "SELECT * FROM dep ORDER BY dep_id";
        List<Department> result = new LinkedList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()){
            statement = connection.prepareStatement(sql_str);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Department department = buildDepartment(resultSet, false);
                result.add(department);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            DBService.close(statement, resultSet);
        }
        return result;
    }

    /**
     * Removes department by ID
     * @param id    Department id
     */
    public void removeDepartment(Integer id) {
        String sql_str = "DELETE FROM dep WHERE dep_id = ?";
        PreparedStatement statement = null;
        try (Connection connection = getConnection()){
            statement = connection.prepareStatement(sql_str);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            DBService.close(statement, null);
        }
    }


    /**
     * Returns department by its ID
     * @param id    Departments id
     * @return      new Department object or null
     */
    public Department getDepartment(Integer id) {
        String sql_str = "SELECT * FROM dep WHERE dep_id = ?";
        Department department = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()){
            statement = connection.prepareStatement(sql_str);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                department = buildDepartment(resultSet, false);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            DBService.close(statement, resultSet);
        }
        return department;
    }

    /**
     * Insert Department into Data base
     * @param department     Department Data Transport Object
     */
    public void addDepartment(Department department) {
        String sql_str = "INSERT INTO dep VALUES (DEFAULT, '"+department.getDepName()+"')";
        Statement statement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(sql_str, Statement.RETURN_GENERATED_KEYS);
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                department.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            DBService.close(statement, resultSet);
        }
    }

    /**
     * Update Department in Data base
     * @param department    Department
     */
    public void updateDepartment(Department department) {
        String sql_str = "UPDATE dep SET dep_name = ? WHERE dep_id = ?";
        PreparedStatement statement = null;
        try (Connection connection = getConnection()) {
            statement = connection.prepareStatement(sql_str);
            statement.setString(1, department.getDepName());
            statement.setInt(2, department.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            DBService.close(statement, null);
        }
    }


    /**
     * Builds department by result set
     * @param resultSet         result set
     * @param includeEmployees  include list of employees
     * @return                  Department object
     * @throws SQLException
     */
    private Department buildDepartment(ResultSet resultSet, boolean includeEmployees) throws SQLException {
        Department department;
        department = new Department();
        department.setId(resultSet.getInt("dep_id"));
        department.setDepName(resultSet.getString("dep_name"));
        if (includeEmployees) {
            department.setEmployees(getEmployeesByDep(department.getId()));
        }
        return department;
    }



    /**
     * Returns list of employees by department id
     * @param departmentId  department id
     * @return              list of employee objects
     */
    public List<Employee> getEmployeesByDep(Integer departmentId) {
        String sql_str = "SELECT * FROM empl WHERE dep_id = ?";
        List<Employee> result = new LinkedList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            statement = connection.prepareStatement(sql_str);
            statement.setInt(1, departmentId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Employee empl = buildEmployee(resultSet);
                result.add(empl);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            DBService.close(statement, resultSet);
        }
        return result;
    }


    /**
     * Return employee by id
     * @param id    employee id
     * @return      employee object
     */
    public Employee getEmployee(Integer id) {
        String sql_str = "SELECT * FROM empl WHERE emp_id = ?";
        Employee empl = null;
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql_str);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                empl = buildEmployee(resultSet);
            }
            DBService.close(statement, resultSet);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return empl;
    }


    /**
     * Removes employee by its id from data base
     * @param id    employee id
     */
    public void removeEmployee(Integer id) {
        String sql_str = "DELETE FROM empl WHERE emp_id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql_str);
            statement.setInt(1, id);
            statement.executeUpdate();
            DBService.close(statement, null);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }


    /**
     * Inserts Employee object into Data base
     * @param employee     Employee
     */
    public void addEmployee(Employee employee) {
        String sql_str = "INSERT INTO empl VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql_str, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, employee.getDepId());
            statement.setString(2, employee.getFirstName());
            statement.setString(3, employee.getLastName());
            java.sql.Date date = employee.getDateOfBirth() == null? null : new java.sql.Date(employee.getDateOfBirth().getTime());
            statement.setDate(4, date);
            statement.setString(5, employee.getEmail());
            statement.setString(6, employee.getPosition());
            statement.setBigDecimal(7, employee.getSalary());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                employee.setId(resultSet.getInt(1));
            }
            DBService.close(statement, resultSet);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }



    /**
     * Updates employee columns in Data base
     * @param employee     Employee Object
     */
    public void updateEmployee(Employee employee) {
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
            java.sql.Date date = employee.getDateOfBirth() == null? null : new java.sql.Date(employee.getDateOfBirth().getTime());
            statement.setDate(4, date);
            statement.setString(5, employee.getEmail());
            statement.setString(6, employee.getPosition());
            statement.setBigDecimal(7, employee.getSalary());
            statement.executeUpdate();
            DBService.close(statement, null);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }


    /**
     * Returns list of used emails
     * @return list of used emails
     */
    public List<String> getUsedEmails() {
        List<String> result = new LinkedList<>();
        String sql_str = "SELECT emp_email FROM empl";
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql_str);
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
            DBService.close(statement, resultSet);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }


    private Employee buildEmployee(ResultSet resultSet) throws SQLException {
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


    /**
     * Parse date string and converts it to a Date object
     * @param strDate   date string format YYYY-MM-DD
     * @return          date object
     */
    public Date parseDate(String strDate) {
        try {
            return simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    public String formatDate(Date date) {
        return simpleDateFormat.format(date);
    }


    /**
     * Validates object and returns list of violation messages
     * @param o     object to validate (must be annotated vith OVal framework annotations)
     * @return      list of messages
     */
    public ErrorMessages validate(Object o) {
        List<ConstraintViolation> violations = new Validator().validate(o);
        ErrorMessages errorMessages = new ErrorMessages();
        errorMessages.setErrors(violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()));
        return errorMessages;
    }


}
