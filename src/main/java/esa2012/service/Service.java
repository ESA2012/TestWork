package esa2012.service;

import esa2012.model.Department;
import esa2012.model.Employee;
import esa2012.datalayer.DepartmentDAOImpl;
import esa2012.datalayer.EmployeeDAOImpl;
import esa2012.datalayer.interfaces.DAOException;
import esa2012.datalayer.interfaces.DepartmentDAO;
import esa2012.datalayer.interfaces.EmployeeDAO;
import esa2012.service.datatransport.DepartmentDTO;
import esa2012.service.datatransport.EmployeeDTO;
import esa2012.service.datatransport.ErrorMessages;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class Service {

    private DepartmentDAO departmentDAO;
    private EmployeeDAO employeeDAO;

    private final static Logger logger = LogManager.getLogger(Service.class);

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private static Service instance;

    private Service() {
        departmentDAO = new DepartmentDAOImpl();
        employeeDAO = new EmployeeDAOImpl();
    }


    public static final Service INSTANCE = new Service();


    /**
     * Return list of departments dto
     * @return  list of departments dto or null is exception
     */
    public List<DepartmentDTO> getDepartments() {
        List<DepartmentDTO> dtoList;
        try {
            dtoList = new LinkedList<>();
            List<Department> departments = departmentDAO.getAll(false);
            for(Department d: departments) {
                dtoList.add(convertToDTO(d));
            }
            return dtoList;
        } catch (DAOException e) {
            logger.error(e.getMessage());
            return null;
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
    public DepartmentDTO getDepartment(Integer id) {
        try {
            return convertToDTO(departmentDAO.getById(id, false));
        } catch (DAOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * Converts Department DTO to Department object and insert it to Data base
     * @param departmentDTO     Department Data Transport Object
     */
    public void addDepartment(DepartmentDTO departmentDTO) {
        try {
            departmentDAO.add(convertFromDTO(departmentDTO));
        } catch (DAOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Converts Department DTO to Department object and update it in Data base
     * @param departmentDTO     Department Data Transport Object
     */
    public void updateDepartment(DepartmentDTO departmentDTO) {
        try {
            departmentDAO.update(convertFromDTO(departmentDTO));
         } catch (DAOException e) {
            logger.error(e.getMessage());
        }
    }


    /**
     * Returns list of employees by department id
     * @param departmentId  department id
     * @return              list of employee objects
     */
    public List<EmployeeDTO> getEmployeesByDep(Integer departmentId) {
        List<EmployeeDTO> dtoList;
        try {
            List<Employee> employees = employeeDAO.getByDepartmentId(departmentId);
            dtoList = new LinkedList<>();
            for (Employee e: employees) {
                dtoList.add(convertToDTO(e));
            }
            return dtoList;
        } catch (DAOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    /**
     * Return employee by id
     * @param id    employee id
     * @return      employee object
     */
    public EmployeeDTO getEmployee(Integer id) {
        try {
            return convertToDTO(employeeDAO.getById(id));
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
     * @param employeeDTO     Employee Data Transport Object
     */
    public void addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertFromDTO(employeeDTO);
        try {
            employeeDAO.add(employee);
        } catch (DAOException e) {
            logger.error(e.getMessage());
        }
    }



    /**
     * Converts Employee Data Transport Object to Employee object and update it in Data base
     * @param employeeDTO     Employee Data Transport Object
     */
    public void updateEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertFromDTO(employeeDTO);
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
            return null;
        }
    }


    /**
     * Converts Employee Data Transport Object to Employee Object
     * @param employeeDTO   Employee Data Transport Object
     * @return              Employee Object
     */
    private Employee convertFromDTO(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        Integer id = employeeDTO.getId() != null? employeeDTO.getId() : null;
        employee.setId(id);
        employee.setDepId(employeeDTO.getDepId());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPosition(employeeDTO.getPosition());
        if (employeeDTO.getSalary() != null && !employeeDTO.getSalary().equals("")) {
            employee.setSalary(new BigDecimal(employeeDTO.getSalary()));
        }
        if (employeeDTO.getDateOfBirth() != null && !employeeDTO.getDateOfBirth().equals("")) {
            employee.setDateOfBirth(parseDate(employeeDTO.getDateOfBirth()));
        }
        return employee;
    }


    /**
     * Converts Department Object to Department Data Transport Object
     * @param department    Department Object
     * @return              Department Data Transport Object
     */
    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(department.getId());
        departmentDTO.setDepName(department.getDepName());
        return departmentDTO;
    }


    /**
     * Converts Department Data Transport Object to Department Object
     * @param departmentDTO Department Data Transport Object
     * @return              Department Object
     */
    private Department convertFromDTO(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setId(departmentDTO.getId());
        department.setDepName(departmentDTO.getDepName());
        return department;
    }



    /**
     * Converts Employee Object to Employee Data Transport Object
     * @param employee  Employee Object
     * @return          Employee Data Transport Object
     */
    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setDepId(employee.getDepId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setPosition(employee.getPosition());
        if (employee.getSalary()!=null) {
            employeeDTO.setSalary(employee.getSalary().toString());
        }
        if (employee.getDateOfBirth()!=null) {
            employeeDTO.setDateOfBirth(simpleDateFormat.format(employee.getDateOfBirth()));
        }
        employeeDTO.setEmail(employee.getEmail());
        return employeeDTO;
    }


    /**
     * Parse date string and converts it to a Date object
     * @param strDate   date string format YYYY-MM-DD
     * @return          date object
     */
    private Date parseDate(String strDate) {
        try {
            return simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            logger.error(e.getMessage());
            return null;
        }
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
