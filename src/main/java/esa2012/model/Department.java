package esa2012.model;

import java.io.Serializable;
import java.util.List;

public class Department implements Serializable {
    private Integer id;
    private String depName;
    private List<Employee> employees;

    public Department() {
    }

    public Department(String depName) {
        this.depName = depName;
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
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            Department other = (Department)o;
            return (depName.equals(other.depName) &&
                    id.equals(other.id) &&
                    employees.equals(other.employees));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + id;
        hash = hash * 31 + depName.hashCode();
        hash = hash * 31 + employees.hashCode();
        return hash;
    }

}
