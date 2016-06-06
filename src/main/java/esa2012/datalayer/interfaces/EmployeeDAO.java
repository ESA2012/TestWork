package esa2012.datalayer.interfaces;

import esa2012.model.Department;
import esa2012.model.Employee;

import java.util.List;

/**
 * Created by snake on 31.05.16.
 */
public interface EmployeeDAO {
    void add(Employee employee) throws DAOException;
    void remove(Integer id) throws DAOException;
    void update(Employee employee) throws DAOException;
    List<Employee> getByDepartment(Department department) throws DAOException;
    List<Employee> getByDepartmentId(Integer departmentId) throws DAOException;
    Employee getById(Integer id) throws DAOException;
    List<String> getEmails() throws DAOException;
}
