package esa2012.service;

import esa2012.model.Department;
import esa2012.model.Employee;
import esa2012.datalayer.DepartmentDAOImpl;
import esa2012.datalayer.EmployeeDAOImpl;
import esa2012.datalayer.interfaces.DAOException;
import esa2012.datalayer.interfaces.DepartmentDAO;
import esa2012.datalayer.interfaces.EmployeeDAO;
import esa2012.service.datatransport.ErrorMessages;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class Service {

    private DepartmentDAO departmentDAO;
    private EmployeeDAO employeeDAO;

    private final static Logger logger = LogManager.getLogger("TestWork app");

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private Service() {
        departmentDAO = new DepartmentDAOImpl();
        employeeDAO = new EmployeeDAOImpl();
    }


    public static final Service INSTANCE = new Service();


    /**
     * Returns list of departments
     * @return  list of departments
     */
    public List<Department> getDepartments() {
        try {
            return departmentDAO.getAll(false);
        } catch (DAOException e) {
            logger.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Removes department by ID
     * @param id    Department id
     */
    public void removeDepartment(Integer id) {
        try {
            departmentDAO.remove(id);
        } catch (DAOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Returns department by ID
     * @param id    Departments id
     * @return      new Department object or null
     */
    public Department getDepartment(Integer id) {
        try {
            return departmentDAO.getById(id, false);
        } catch (DAOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * Insert Department to Data base
     * @param department     Department Data Transport Object
     */
    public void addDepartment(Department department) {
        try {
            departmentDAO.add(department);
        } catch (DAOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Update Department in Data base
     * @param department    Department
     */
    public void updateDepartment(Department department) {
        try {
            departmentDAO.update(department);
         } catch (DAOException e) {
            logger.error(e.getMessage());
        }
    }


    /**
     * Returns list of employees by department id
     * @param departmentId  department id
     * @return              list of employee objects
     */
    public List<Employee> getEmployeesByDep(Integer departmentId) {
        try {
            return employeeDAO.getByDepartmentId(departmentId);
        } catch (DAOException e) {
            logger.error(e.getMessage());
            return Collections.emptyList();
        }
    }


    /**
     * Return employee by id
     * @param id    employee id
     * @return      employee object
     */
    public Employee getEmployee(Integer id) {
        try {
            return employeeDAO.getById(id);
        } catch (DAOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    /**
     * Removes employee by its id from data base
     * @param id    employee id
     */
    public void removeEmployee(Integer id) {
        try {
            employeeDAO.remove(id);
        } catch (DAOException e) {
            logger.error(e.getMessage());
        }
    }


    /**
     * Converts Employee Data Transport Object to Employee object and insert it to Data base
     * @param employee     Employee Data Transport Object
     */
    public void addEmployee(Employee employee) {
        try {
            employeeDAO.add(employee);
        } catch (DAOException e) {
            logger.error(e.getMessage());
        }
    }



    /**
     * Update it in Data base
     * @param employee     Employee Data Transport Object
     */
    public void updateEmployee(Employee employee) {
        try {
            employeeDAO.update(employee);
        } catch (DAOException e) {
            logger.error(e.getMessage());
         }
    }


    /**
     * Returns list of used emails
     * @return list of used emails
     */
    public List<String> getUsedEmails() {
        try {
            return employeeDAO.getEmails();
        } catch (DAOException e) {
            logger.error(e.getMessage());
            return Collections.emptyList();
        }
    }


    /**
     * Parse date string and converts it to a Date object
     * @param strDate   date string format YYYY-MM-DD
     * @return          date object
     */
    public static Date parseDate(String strDate) {
        try {
            return simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    public static String formatDate(Date date) {
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
