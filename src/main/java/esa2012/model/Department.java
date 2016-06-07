package esa2012.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Department implements Serializable {
    private Integer id;
    private String depName;
    private List<Employee> employees;

    public Department() {
    }

    public String getDepName() {
        return depName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(depName, that.depName) &&
                Objects.equals(employees, that.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, depName, employees);
    }
}
