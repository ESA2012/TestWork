package esa2012.datalayer.interfaces;

import esa2012.model.Department;

import java.util.List;

/**
 * Created by snake on 31.05.16.
 */
public interface DepartmentDAO {
    List<Department> getAll(boolean includeEmployees) throws DAOException;
    void add(Department department) throws DAOException;
    void remove(Integer id) throws DAOException;
    void update(Department department) throws DAOException;
    Department getById(Integer id, boolean includeEmployees) throws DAOException;
}
